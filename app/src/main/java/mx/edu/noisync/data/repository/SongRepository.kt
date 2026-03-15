package mx.edu.noisync.data.repository

import mx.edu.noisync.data.model.SongDetail
import mx.edu.noisync.data.model.SongListItem

data class PageResult<T>(
    val content: List<T>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
    val last: Boolean
)

interface SongRepository {
    suspend fun getVisibleSongs(
        query: String? = null,
        page: Int = 0,
        size: Int = 10
    ): RepositoryResult<PageResult<SongListItem>>

    suspend fun getSongDetail(songId: String): RepositoryResult<SongDetail>
}
