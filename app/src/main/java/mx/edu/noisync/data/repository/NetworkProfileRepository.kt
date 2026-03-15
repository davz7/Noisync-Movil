package mx.edu.noisync.data.repository

import mx.edu.noisync.data.model.ChangePasswordRequest
import mx.edu.noisync.data.model.ChangePasswordResponse
import mx.edu.noisync.data.model.UserProfile
import mx.edu.noisync.core.network.ApiService
import mx.edu.noisync.data.remote.dto.toDomain

class NetworkProfileRepository(
    private val apiService: ApiService
) : ProfileRepository {

    override suspend fun getProfile(): RepositoryResult<UserProfile> {
        return try {
            val response = apiService.getMe()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    RepositoryResult.Success(body.toDomain())
                } else {
                    RepositoryResult.Error("Respuesta del servidor vacia", response.code())
                }
            } else {
                RepositoryResult.Error(
                    message = response.message().ifBlank { "Error ${response.code()}" },
                    code = response.code()
                )
            }
        } catch (exception: Exception) {
            RepositoryResult.Error("Sin conexion al servidor")
        }
    }

    override suspend fun changePassword(request: ChangePasswordRequest): RepositoryResult<ChangePasswordResponse> {
        return try {
            val response = apiService.changePassword(request)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    RepositoryResult.Success(body.toDomain())
                } else {
                    RepositoryResult.Error("Respuesta del servidor vacia", response.code())
                }
            } else {
                RepositoryResult.Error(
                    message = response.message().ifBlank { "Error ${response.code()}" },
                    code = response.code()
                )
            }
        } catch (exception: Exception) {
            RepositoryResult.Error("Sin conexion al servidor")
        }
    }
}
