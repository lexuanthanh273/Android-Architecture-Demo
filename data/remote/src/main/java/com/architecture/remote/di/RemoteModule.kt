package com.architecture.remote.di

import android.app.Application
import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.architecture.preference.UserDataStorePreferences
import com.architecture.remote.ApiConstants
import com.architecture.remote.NetworkResponseAdapterFactory
import com.architecture.remote.api.refreshtoken.RefreshTokenService
import com.architecture.remote.authenticator.TokenAuthenticator
import com.architecture.remote.interceptor.AuthTokenInterceptor
import com.architecture.remote.moshi.BigDecimalAdapter
import com.architecture.remote.websocket.adapter.BitmapMessageAdapter
import com.architecture.remote.websocket.adapter.MoshiMessageAdapter
import com.architecture.remote.websocket.adapter.steram.coroutines.CoroutinesStreamAdapterFactory
import com.architecture.remote.websocket.adapter.steram.rxjava2.RxJava2StreamAdapterFactory
import com.architecture.remote.websocket.di.LoggedInLifecycle
import com.architecture.remote.websocket.lifecycle.AndroidLifecycle
import com.architecture.remote.websocket.okhttp.newWebSocketFactory
import com.architecture.remote.websocket.service.echo.EchoService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.retry.ExponentialWithJitterBackoffStrategy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    companion object {
        private const val REQUEST_TIMEOUT = 10L
        private val backoffStrategy = ExponentialWithJitterBackoffStrategy(5000, 5000)
    }


    @Provides
    @Singleton
    fun provideLifecycle(
        @ApplicationContext applicationContext: Context,
        loggedInLifecycle: LoggedInLifecycle
    ): Lifecycle =
        AndroidLifecycle
            .ofApplicationForeground(applicationContext as Application)
            .combineWith(loggedInLifecycle)

    @Provides
    @Singleton
    fun provideCoroutineScopeToRunIn(): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }

    @Provides
    @Singleton
    fun provideEchoService(
        lifecycle: Lifecycle,
        moshi: Moshi,
        dataStorePreferences: UserDataStorePreferences
    ): EchoService {
        val accessToken = runBlocking {
            dataStorePreferences.accessToken.first()
        }
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor{ chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Connection", "close")
                    .addHeader("Accept-Encoding", "identity")
                    .build()
                chain.proceed(request)
            }
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        val scarlet = Scarlet.Builder()
            .webSocketFactory(client.newWebSocketFactory("ws://demo/socket/join?access_token=$accessToken"))
            .lifecycle(lifecycle)
            .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
            .addMessageAdapterFactory(BitmapMessageAdapter.Factory())
            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
            .addStreamAdapterFactory(CoroutinesStreamAdapterFactory())
            .backoffStrategy(backoffStrategy)
            .build()
        return scarlet.create()
    }

    @Provides
    @Singleton
    internal fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    internal fun authTokenInterceptor(dataStorePreferences: UserDataStorePreferences): AuthTokenInterceptor {
        return AuthTokenInterceptor(dataStorePreferences)
    }

    @Provides
    @Singleton
    internal fun tokenAuthenticator(
        @ApplicationContext applicationContext: Context,
        dataStorePreferences: UserDataStorePreferences,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authTokenInterceptor: AuthTokenInterceptor,
        moshi: Moshi
    ): TokenAuthenticator {
        val refreshTokenService = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(getOkhttpClient(applicationContext, httpLoggingInterceptor, authTokenInterceptor))
            .build()
            .create(RefreshTokenService::class.java)

        return TokenAuthenticator(refreshTokenService, dataStorePreferences)
    }

    @Provides
    @Singleton
    internal fun provideOkhttpClient(
        @ApplicationContext applicationContext: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authTokenInterceptor: AuthTokenInterceptor,
        tokenAuthenticator: TokenAuthenticator,
    ): OkHttpClient {
        return getOkhttpClient(applicationContext, httpLoggingInterceptor, authTokenInterceptor, tokenAuthenticator)
    }

    private fun getOkhttpClient(
        applicationContext: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authTokenInterceptor: AuthTokenInterceptor,
        tokenAuthenticator: TokenAuthenticator? = null,
    ): OkHttpClient {
        val chuckerCollector = ChuckerCollector(
            context = applicationContext,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        val chuckerInterceptor = ChuckerInterceptor.Builder(applicationContext)
            .collector(chuckerCollector)
            .build()

        val builder = OkHttpClient.Builder()
            .protocols(listOf(Protocol.HTTP_1_1))
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authTokenInterceptor)
            .addInterceptor(OkHttpProfilerInterceptor())
//            .addInterceptor(GzipRequestInterceptor())
            .also { client ->
                tokenAuthenticator?.let {
                    client.authenticator(it)
                }
            }
            .addNetworkInterceptor{ chain ->
                val request = chain.request().newBuilder()
                    .header("Connection", "close")
                    .header("Accept-Encoding", "identity")
                    .header("Accept-Encoding", "gzip, deflate")
                    .build()
                chain.proceed(request)
            }
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)

//        if (BuildConfig.DEBUG) {
//            builder.addInterceptor(OkHttpProfilerInterceptor())
//        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(BigDecimalAdapter)
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
//            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

}