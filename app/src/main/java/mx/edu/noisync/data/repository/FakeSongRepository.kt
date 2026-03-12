package mx.edu.noisync.data.repository

import mx.edu.noisync.data.fake.FakeSongs
import mx.edu.noisync.model.SongDetail
import mx.edu.noisync.model.SongListItem

class FakeSongRepository : SongRepository {
    override suspend fun getVisibleSongs(
        query: String?,
        page: Int,
        size: Int
    ): RepositoryResult<PageResult<SongListItem>> {
        val normalizedQuery = query.orEmpty().trim().lowercase()
        val filtered = FakeSongs.accessibleSongs.filter { song ->
            normalizedQuery.isBlank() ||
                song.title.lowercase().contains(normalizedQuery) ||
                song.artistName.lowercase().contains(normalizedQuery)
        }

        val fromIndex = page * size
        val content = filtered.drop(fromIndex).take(size)

        return RepositoryResult.Success(
            PageResult(
                content = content,
                page = page,
                size = size,
                totalElements = filtered.size.toLong(),
                totalPages = if (filtered.isEmpty()) 0 else ((filtered.size - 1) / size) + 1,
                last = fromIndex + size >= filtered.size
            )
        )
    }

    override suspend fun getSongDetail(songId: String): RepositoryResult<SongDetail> {
        return RepositoryResult.Success(FakeSongs.getSongDetail(songId))
    }
}
