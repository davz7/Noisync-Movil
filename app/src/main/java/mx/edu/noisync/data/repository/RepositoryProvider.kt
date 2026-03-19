package mx.edu.noisync.data.repository

import mx.edu.noisync.core.network.RetrofitClient

object RepositoryProvider {
    val songRepository: SongRepository by lazy {
        NetworkSongRepository(RetrofitClient.instance)
    }

    val profileRepository: ProfileRepository by lazy {
        NetworkProfileRepository(RetrofitClient.instance)
    }

    val musicianRepository: MusicianRepository by lazy {
        NetworkMusicianRepository(RetrofitClient.instance)
    }

    val instrumentRepository: InstrumentRepository by lazy {
        NetworkInstrumentRepository(RetrofitClient.instance)
    }
}
