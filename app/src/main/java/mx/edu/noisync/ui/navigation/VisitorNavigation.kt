package mx.edu.noisync.ui.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mx.edu.noisync.ui.visitor.SongDetailScreen
import mx.edu.noisync.ui.visitor.VisitorSongsFilter
import mx.edu.noisync.ui.visitor.VisitorSongsUiState
import mx.edu.noisync.ui.visitor.VisitorHomeScreen
import mx.edu.noisync.ui.visitor.VisitorSongsViewModel

@Composable
fun VisitorNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppsScreens.VisitorHomeScreen.route) {
        composable(route = AppsScreens.VisitorHomeScreen.route) {
            val viewModel: VisitorSongsViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()
            val searchQuery by viewModel.searchQuery.collectAsState()
            val selectedFilter by viewModel.selectedFilter.collectAsState()
            val songs = (uiState as? VisitorSongsUiState.Success)?.songs.orEmpty()

            VisitorHomeScreen(
                songs = songs,
                searchQuery = searchQuery,
                onSearchQueryChange = viewModel::onSearchQueryChange,
                selectedFilter = selectedFilter,
                onShowAll = { viewModel.selectFilter(VisitorSongsFilter.ALL) },
                onShowRecent = { viewModel.selectFilter(VisitorSongsFilter.RECENT) },
                isLoading = uiState is VisitorSongsUiState.Loading,
                errorMessage = (uiState as? VisitorSongsUiState.Error)?.message,
                onRetry = { viewModel.loadSongs(searchQuery.takeIf { it.isNotBlank() }) },
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
                songId = backStackEntry.arguments?.getString(AppsScreens.SongDetailScreen.ARG_SONG_ID),
                isPublicSong = true
            )
        }
    }
}
