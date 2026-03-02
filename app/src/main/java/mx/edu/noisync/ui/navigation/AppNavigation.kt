package mx.edu.noisync.ui.navigation

import  mx.edu.noisync.ui.navigation.AppsScreens.VisitorHomeScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.edu.noisync.data.fake.FakeSongs
import mx.edu.noisync.ui.visitor.SongDetailScreen
import mx.edu.noisync.ui.visitor.VisitorHomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = VisitorHomeScreen.route) {

        composable(route = VisitorHomeScreen.route) {
            VisitorHomeScreen(
                navController,
                songs = FakeSongs.publicSongs,
                onOpenSong = { song ->
                    navController.navigate(AppsScreens.SongDetailScreen.route)
                }
            )
        }
        composable(route = AppsScreens.SongDetailScreen.route) {
            SongDetailScreen(navController)
        }

    }
}