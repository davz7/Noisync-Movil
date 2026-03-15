package mx.edu.noisync.data.repository

import mx.edu.noisync.core.network.RetrofitClient

object RepositoryProvider {
    val songRepository: SongRepository by lazy {
        NetworkSongRepository(RetrofitClient.instance)
    }

    val profileRepository: ProfileRepository by lazy {
        NetworkProfileRepository(RetrofitClient.instance)
    }

    val fakeSongRepository: SongRepository by lazy {
        FakeSongRepository()
    }
}
