package mx.edu.noisync.data.network

import mx.edu.noisync.data.model.LoginRequest
import mx.edu.noisync.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    
}
