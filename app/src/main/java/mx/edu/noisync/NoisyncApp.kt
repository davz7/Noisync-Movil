package mx.edu.noisync

import android.app.Application
import coil.ImageLoader
import coil.SingletonImageLoader
import mx.edu.noisync.core.network.RetrofitClient

class NoisyncApp : Application(), SingletonImageLoader.Factory {

    override fun onCreate() {
        super.onCreate()
        RetrofitClient.init(this)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .okHttpClient(RetrofitClient.getOkHttpClient(this))
            .build()
    }
}
