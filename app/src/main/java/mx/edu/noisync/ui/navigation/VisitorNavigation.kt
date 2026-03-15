package mx.edu.noisync.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mx.edu.noisync.fake.FakeSongs
import mx.edu.noisync.ui.visitor.SongDetailScreen
import mx.edu.noisync.ui.visitor.VisitorHomeScreen

@Composable
fun VisitorNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppsScreens.VisitorHomeScreen.route) {
        composable(route = AppsScreens.VisitorHomeScreen.route) {
            VisitorHomeScreen(
                songs = FakeSongs.publicSongs,
                onOpenSong = { song ->
                    navController.navigate(AppsScreens.SongDetailScreen.createRoute(song.id))
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
    }
}
