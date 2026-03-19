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
import mx.edu.noisync.ui.instruments.UserInstrumentsScreen
import mx.edu.noisync.ui.profile.UserInfo
import mx.edu.noisync.ui.songs.SongDetailScreen
import mx.edu.noisync.ui.songs.UserHomeScreen
import mx.edu.noisync.ui.songs.UserSongsFilter
import mx.edu.noisync.ui.songs.UserSongsUiState
import mx.edu.noisync.ui.songs.UserSongsViewModel
import mx.edu.noisync.ui.team.UserTeamScreen

@Composable
fun MusicianNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppsScreens.MusicianHomeScreen.route) {
        composable(route = AppsScreens.MusicianHomeScreen.route) {
            val viewModel: UserSongsViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()
            val searchQuery by viewModel.searchQuery.collectAsState()
            val selectedFilter by viewModel.selectedFilter.collectAsState()
            val songs = (uiState as? UserSongsUiState.Success)?.songs.orEmpty()

            UserHomeScreen(
                songs = songs,
                searchQuery = searchQuery,
                onSearchQueryChange = viewModel::onSearchQueryChange,
                selectedFilter = selectedFilter,
                onShowAll = { viewModel.selectFilter(UserSongsFilter.ALL) },
                onShowPublic = { viewModel.selectFilter(UserSongsFilter.PUBLIC) },
                onShowPrivate = { viewModel.selectFilter(UserSongsFilter.PRIVATE) },
                isLoading = uiState is UserSongsUiState.Loading,
                errorMessage = (uiState as? UserSongsUiState.Error)?.message,
                onRetry = { viewModel.loadSongs(searchQuery.takeIf { it.isNotBlank() }) },
                onOpenSong = { song ->
                    navController.navigate(AppsScreens.SongDetailScreen.createRoute(song.id))
                },
                onOpenTeam = {
                    navController.navigate(AppsScreens.MusicianTeamScreen.route)
                },
                onOpenInstruments = {
                    navController.navigate(AppsScreens.MusicianInstrumentsScreen.route)
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
                songId = backStackEntry.arguments?.getString(AppsScreens.SongDetailScreen.ARG_SONG_ID),
                isPublicSong = false
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
