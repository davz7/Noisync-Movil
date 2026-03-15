package mx.edu.noisync.core.network

import android.content.Context
import mx.edu.noisync.data.local.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(context: Context) : Interceptor {
    
    private val sessionManager = SessionManager(context)
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        val token = sessionManager.getAccessToken()
        
        val request = if (token != null) {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }
        
        return chain.proceed(request)
    }
}
