package mx.edu.noisync.data.repository

import mx.edu.noisync.core.network.ApiService
import mx.edu.noisync.data.model.Musician
import mx.edu.noisync.data.remote.dto.toDomain

class NetworkMusicianRepository(
    private val apiService: ApiService
) : MusicianRepository {

    override suspend fun getMusicians(): RepositoryResult<List<Musician>> {
        return try {
            val response = apiService.getMusicians()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    RepositoryResult.Success(body.map { it.toDomain() })
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
