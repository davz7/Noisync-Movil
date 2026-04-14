package mx.edu.noisync.core.network

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    //private const val BASE_URL = "http://10.0.2.2:8080/"
    private const val BASE_URL = "http://192.168.0.123:8080/"

    @Volatile
    private var apiService: ApiService? = null

    @Volatile
    private var cachedOkHttpClient: OkHttpClient? = null

    fun init(context: Context) {
        if (apiService != null) {
            return
        }

        synchronized(this) {
            if (apiService == null) {
                apiService = createRetrofit(context.applicationContext).create(ApiService::class.java)
            }
        }
    }

    fun getOkHttpClient(context: Context): OkHttpClient {
        if (cachedOkHttpClient != null) return cachedOkHttpClient!!
        return synchronized(this) {
            cachedOkHttpClient ?: createOkHttpClient(context.applicationContext).also {
                cachedOkHttpClient = it
            }
        }
    }

    val instance: ApiService
        get() = apiService ?: error("RetrofitClient no esta inicializado")

    fun resolveUrl(url: String?): String? {
        val normalizedUrl = url?.trim()?.takeIf { it.isNotEmpty() } ?: return null

        if (normalizedUrl.startsWith("http://") || normalizedUrl.startsWith("https://")) {
            return normalizeAbsoluteUrl(normalizedUrl)
        }

        val baseUrl = BASE_URL.removeSuffix("/")
        return if (normalizedUrl.startsWith("/")) {
            baseUrl + normalizedUrl
        } else {
            "$baseUrl/$normalizedUrl"
        }
    }

    private fun createRetrofit(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createOkHttpClient(context: Context): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .addInterceptor(loggingInterceptor)
            .authenticator(SessionExpirationAuthenticator(context))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private fun normalizeAbsoluteUrl(url: String): String {
        return try {
            val base = java.net.URI(BASE_URL)
            val uri = java.net.URI(url)
            val host = uri.host?.lowercase()

            if (host == "localhost" || host == "127.0.0.1" || host == "10.0.2.2") {
                java.net.URI(
                    base.scheme,
                    uri.userInfo,
                    base.host,
                    base.port,
                    uri.path,
                    uri.query,
                    uri.fragment
                ).toString()
            } else {
                url
            }
        } catch (_: Exception) {
            url
        }
    }
}
