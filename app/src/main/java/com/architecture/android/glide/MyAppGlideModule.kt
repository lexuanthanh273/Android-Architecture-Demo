package com.architecture.android.glide

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import java.io.InputStream
import javax.inject.Inject

@GlideModule
class MyAppGlideModule @Inject constructor (): AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setLogLevel(Log.DEBUG)
        builder.setDefaultRequestOptions(
            RequestOptions().format(DecodeFormat.PREFER_RGB_565).disallowHardwareConfig()
        )
        val bitmapPoolSizeBytes = 1024L * 1024 * 30 // 30mb

        val memoryCacheSizeBytes = 1024L * 1024 * 20 // 20mb

        val diskCacheSizeBytes = 1024L * 1024 * 100 // 100 MB

        builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes))
        builder.setBitmapPool(LruBitmapPool(bitmapPoolSizeBytes))
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, diskCacheSizeBytes))
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.prepend(String::class.java, InputStream::class.java, CustomUrlModelLoaderFactory())
    }
}