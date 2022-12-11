package com.architecture.android.glide

import com.bumptech.glide.load.model.*
import java.io.InputStream

class CustomUrlModelLoaderFactory(
) : ModelLoaderFactory<String, InputStream> {
    private val modelCache = ModelCache<String, GlideUrl>(500)

    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<String, InputStream> {
        return CustomUrlModelLoader(
            multiFactory.build(GlideUrl::class.java, InputStream::class.java),
            modelCache
        )
    }

    override fun teardown() {}

}