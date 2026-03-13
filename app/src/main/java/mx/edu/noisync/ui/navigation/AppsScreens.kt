package mx.edu.noisync.ui.navigation

sealed class AppsScreens(val route: String) {
    object SongDetailScreen : AppsScreens("song_detail/{songId}") {
        fun createRoute(songId: String): String = "song_detail/$songId"
        const val ARG_SONG_ID = "songId"
    }

    object VisitorHomeScreen : AppsScreens("visitor_home")

    object MusicianHomeScreen : AppsScreens("musician_home")
    object MusicianTeamScreen : AppsScreens("musician_team")
    object MusicianInstrumentsScreen : AppsScreens("musician_instruments")
    object MusicianProfileScreen : AppsScreens("musician_profile")

    object LeaderHomeScreen : AppsScreens("leader_home")
    object LeaderTeamScreen : AppsScreens("leader_team")
    object LeaderInstrumentsScreen : AppsScreens("leader_instruments")
    object LeaderProfileScreen : AppsScreens("leader_profile")
}
