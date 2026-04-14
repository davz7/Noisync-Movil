package mx.edu.noisync.ui.navigation

sealed class AppsScreens(val route: String) {
    object SongDetailScreen : AppsScreens("song_detail/{songId}/{isPublicSong}") {
        fun createRoute(songId: String, isPublicSong: Boolean): String =
            "song_detail/$songId/$isPublicSong"

        const val ARG_SONG_ID = "songId"
        const val ARG_IS_PUBLIC = "isPublicSong"
    }

    object VisitorHomeScreen : AppsScreens("visitor_home")

    object MusicianHomeScreen : AppsScreens("musician_home")
    object MusicianMySongsScreen : AppsScreens("musician_my_songs")
    object MusicianTeamScreen : AppsScreens("musician_team")
    object MusicianInstrumentsScreen : AppsScreens("musician_instruments")
    object MusicianProfileScreen : AppsScreens("musician_profile")

    object LeaderHomeScreen : AppsScreens("leader_home")
    object LeaderMySongsScreen : AppsScreens("leader_my_songs")
    object LeaderTeamScreen : AppsScreens("leader_team")
    object LeaderInstrumentsScreen : AppsScreens("leader_instruments")
    object LeaderProfileScreen : AppsScreens("leader_profile")
}
