package mx.edu.noisync.data.repository

import mx.edu.noisync.core.network.ApiService
import mx.edu.noisync.data.model.SongDetail
import mx.edu.noisync.data.model.SongListItem
import mx.edu.noisync.data.remote.dto.PageResponseDto
import mx.edu.noisync.data.remote.dto.SectionResponseDto
import mx.edu.noisync.data.remote.dto.SongResponseDto
import mx.edu.noisync.data.remote.dto.toDetail
import mx.edu.noisync.data.remote.dto.toListItem
import retrofit2.Response

class NetworkSongRepository(
    private val apiService: ApiService
) : SongRepository {
    override suspend fun getPublicSongs(
        query: String?,
        page: Int,
        size: Int
    ): RepositoryResult<PageResult<SongListItem>> {
        return fetchSongPage { apiService.getPublicSongs(query = query, page = page, size = size) }
    }

    override suspend fun getVisibleSongs(
        query: String?,
        page: Int,
        size: Int
    ): RepositoryResult<PageResult<SongListItem>> {
        return fetchSongPage { apiService.getSongs(query = query, page = page, size = size) }
    }

    override suspend fun getPublicSongDetail(songId: String): RepositoryResult<SongDetail> {
        return fetchSongDetail(
            loadDetail = { apiService.getPublicSongDetail(songId) },
            loadSections = { apiService.getPublicSongSections(songId) }
        )
    }

    override suspend fun getSongDetail(songId: String): RepositoryResult<SongDetail> {
        return fetchSongDetail(
            loadDetail = { apiService.getPrivateSongDetail(songId) },
            loadSections = { apiService.getPrivateSongSections(songId) }
        )
    }

    private suspend fun fetchSongPage(
        request: suspend () -> Response<PageResponseDto<SongResponseDto>>
    ): RepositoryResult<PageResult<SongListItem>> {
        return try {
            val response = request()

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

    private suspend fun fetchSongDetail(
        loadDetail: suspend () -> Response<SongResponseDto>,
        loadSections: suspend () -> Response<List<SectionResponseDto>>
    ): RepositoryResult<SongDetail> {
        return try {
            val detailResponse = loadDetail()
            if (!detailResponse.isSuccessful) {
                return RepositoryResult.Error(
                    message = detailResponse.message().ifBlank { "Error ${detailResponse.code()}" },
                    code = detailResponse.code()
                )
            }

            val detailBody = detailResponse.body()
                ?: return RepositoryResult.Error("Respuesta del servidor vacia", detailResponse.code())

            val sectionsResponse = loadSections()
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
