package com.architecture.android.glide

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelCache
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import java.io.InputStream

class CustomUrlModelLoader(
    concreteLoader: ModelLoader<GlideUrl, InputStream>,
    modelCache: ModelCache<String, GlideUrl>
) : BaseGlideUrlLoader<String>(concreteLoader, modelCache) {

    override fun handles(model: String): Boolean {
        return model.startsWith("/upload/image/")
    }

    override fun getUrl(model: String?, width: Int, height: Int, options: Options?): String {
        return "http://demo.com$model"
//        + "?w=" + width + "&h=" + height
    }
}