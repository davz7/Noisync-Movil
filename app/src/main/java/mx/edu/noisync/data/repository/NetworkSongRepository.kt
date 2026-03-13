package mx.edu.noisync.data.repository

import mx.edu.noisync.data.network.ApiService
import mx.edu.noisync.data.remote.dto.toDetail
import mx.edu.noisync.data.remote.dto.toListItem
import mx.edu.noisync.model.SongDetail
import mx.edu.noisync.model.SongListItem

class NetworkSongRepository(
    private val apiService: ApiService
) : SongRepository {

    override suspend fun getVisibleSongs(
        query: String?,
        page: Int,
        size: Int
    ): RepositoryResult<PageResult<SongListItem>> {
        return try {
            val response = apiService.getSongs(
                query = query,
                page = page,
                size = size
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    RepositoryResult.Success(
                        PageResult(
                            content = body.content.map { it.toListItem() },
                            page = body.page,
                            size = body.size,
                            totalElements = body.totalElements,
                            totalPages = body.totalPages,
                            last = body.last
                        )
                    )
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

    override suspend fun getSongDetail(songId: String): RepositoryResult<SongDetail> {
        return try {
            val detailResponse = apiService.getSongDetail(songId)
            if (!detailResponse.isSuccessful) {
                return RepositoryResult.Error(
                    message = detailResponse.message().ifBlank { "Error ${detailResponse.code()}" },
                    code = detailResponse.code()
                )
            }

            val detailBody = detailResponse.body()
                ?: return RepositoryResult.Error("Respuesta del servidor vacia", detailResponse.code())

            val sectionsResponse = apiService.getSongSections(songId)
            if (!sectionsResponse.isSuccessful) {
                return RepositoryResult.Error(
                    message = sectionsResponse.message().ifBlank { "Error ${sectionsResponse.code()}" },
                    code = sectionsResponse.code()
                )
            }

            val sectionsBody = sectionsResponse.body().orEmpty()
            RepositoryResult.Success(detailBody.toDetail(sectionsBody))
        } catch (exception: Exception) {
            RepositoryResult.Error("Sin conexion al servidor")
        }
    }
}
