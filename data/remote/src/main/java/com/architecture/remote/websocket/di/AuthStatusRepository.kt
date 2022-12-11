package com.architecture.remote.websocket.di

import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import javax.inject.Inject

class AuthStatusRepository @Inject constructor() {

    private val authStatusProcessor = BehaviorProcessor.createDefault(AuthStatus.LOGGED_IN)

    fun getAuthStatus(): AuthStatus = authStatusProcessor.value!!

    fun observeAuthStatus(): Flowable<AuthStatus> = authStatusProcessor

    fun updateAuthStatus(authStatus: AuthStatus) = authStatusProcessor.onNext(authStatus)
}
