package mx.edu.noisync.data.repository

import mx.edu.noisync.data.model.Musician

interface MusicianRepository {
    suspend fun getMusicians(): RepositoryResult<List<Musician>>
}
