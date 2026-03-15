package mx.edu.noisync.core.network

import mx.edu.noisync.data.model.LoginRequest
import mx.edu.noisync.data.model.LoginResponse
import mx.edu.noisync.data.model.ChangePasswordRequest
import mx.edu.noisync.data.remote.dto.ChangePasswordResponseDto
import mx.edu.noisync.data.remote.dto.MeResponseDto
import mx.edu.noisync.data.remote.dto.PageResponseDto
import mx.edu.noisync.data.remote.dto.SectionResponseDto
import mx.edu.noisync.data.remote.dto.SongResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/auth/change-password")
    suspend fun changePassword(@Body request: ChangePasswordRequest): Response<ChangePasswordResponseDto>

    @GET("api/me")
    suspend fun getMe(): Response<MeResponseDto>

    @GET("api/songs")
    suspend fun getSongs(
        @Query("q") query: String? = null,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10
    ): Response<PageResponseDto<SongResponseDto>>

    @GET("api/songs/{songId}")
    suspend fun getSongDetail(
        @Path("songId") songId: String
    ): Response<SongResponseDto>

    @GET("api/songs/{songId}/sections")
    suspend fun getSongSections(
        @Path("songId") songId: String
    ): Response<List<SectionResponseDto>>
}
