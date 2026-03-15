package mx.edu.noisync.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mx.edu.noisync.fake.FakeSongs
import mx.edu.noisync.ui.user.UserHomeScreen
import mx.edu.noisync.ui.user.UserInfo
import mx.edu.noisync.ui.user.UserInstrumentsScreen
import mx.edu.noisync.ui.user.UserTeamScreen
import mx.edu.noisync.ui.visitor.SongDetailScreen

@Composable
fun LeaderNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppsScreens.LeaderHomeScreen.route) {
        composable(route = AppsScreens.LeaderHomeScreen.route) {
            UserHomeScreen(
                songs = FakeSongs.accessibleSongs,
                onOpenSong = { song ->
                    navController.navigate(AppsScreens.SongDetailScreen.createRoute(song.id))
                },
                onOpenProfile = {
                    navController.navigate(AppsScreens.LeaderProfileScreen.route)
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

        composable(route = AppsScreens.LeaderTeamScreen.route) {
            UserTeamScreen(navController = navController)
        }

        composable(route = AppsScreens.LeaderInstrumentsScreen.route) {
            UserInstrumentsScreen(navController = navController)
        }

        composable(route = AppsScreens.LeaderProfileScreen.route) {
            UserInfo(navController = navController)
        }
    }
}
