package mx.edu.noisync.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mx.edu.noisync.data.fake.FakeSongs
import mx.edu.noisync.ui.user.UserHomeScreen
import mx.edu.noisync.ui.user.UserInfo
import mx.edu.noisync.ui.user.UserInstrumentsScreen
import mx.edu.noisync.ui.user.UserTeamScreen
import mx.edu.noisync.ui.visitor.SongDetailScreen

@Composable
fun MusicianNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppsScreens.MusicianHomeScreen.route) {
        composable(route = AppsScreens.MusicianHomeScreen.route) {
            UserHomeScreen(
                songs = FakeSongs.accessibleSongs,
                onOpenSong = { song ->
                    navController.navigate(AppsScreens.SongDetailScreen.createRoute(song.id))
                },
                onOpenProfile = {
                    navController.navigate(AppsScreens.MusicianProfileScreen.route)
                }
            )
        }

        composable(
            route = AppsScreens.SongDetailScreen.route,
            arguments = listOf(navArgument(AppsScreens.SongDetailScreen.ARG_SONG_ID) { type = NavType.StringType })
        ) { backStackEntry ->
            SongDetailScreen(
                navController = navController,
                songId = backStackEntry.arguments?.getString(AppsScreens.SongDetailScreen.ARG_SONG_ID)
            )
        }

        composable(route = AppsScreens.MusicianTeamScreen.route) {
            UserTeamScreen(navController = navController)
        }

        composable(route = AppsScreens.MusicianInstrumentsScreen.route) {
            UserInstrumentsScreen(navController = navController)
        }

        composable(route = AppsScreens.MusicianProfileScreen.route) {
            UserInfo(navController = navController)
        }
    }
}
