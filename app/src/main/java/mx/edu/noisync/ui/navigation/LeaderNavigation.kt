package mx.edu.noisync.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import mx.edu.noisync.ui.songs.UserSongsMode
import mx.edu.noisync.ui.songs.UserSongsUiState
import mx.edu.noisync.ui.songs.UserSongsViewModel
import mx.edu.noisync.ui.team.UserTeamScreen

@Composable
fun LeaderNavigation() {
    val navController = rememberNavController()
    fun navigateFromBottomBar(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            restoreState = true
            popUpTo(AppsScreens.LeaderHomeScreen.route) {
                saveState = true
            }
        }
    }

    NavHost(navController = navController, startDestination = AppsScreens.LeaderHomeScreen.route) {
        composable(route = AppsScreens.LeaderHomeScreen.route) {
            val viewModel: UserSongsViewModel = viewModel()
            LaunchedEffect(viewModel) {
                viewModel.setMode(UserSongsMode.GENERAL)
            }
            val uiState by viewModel.uiState.collectAsState()
            val searchQuery by viewModel.searchQuery.collectAsState()
            val selectedFilter by viewModel.selectedFilter.collectAsState()
            val songs = (uiState as? UserSongsUiState.Success)?.songs.orEmpty()

            UserHomeScreen(
                songs = songs,
                title = "Canciones",
                showFilters = false,
                selectedDestination = mx.edu.noisync.ui.components.AuthenticatedDestination.SONGS,
                searchQuery = searchQuery,
                onSearchQueryChange = viewModel::onSearchQueryChange,
                selectedFilter = selectedFilter,
                onShowAll = { viewModel.selectFilter(UserSongsFilter.ALL) },
                onShowRecent = { viewModel.selectFilter(UserSongsFilter.RECENT) },
                onShowPrivate = { viewModel.selectFilter(UserSongsFilter.PRIVATE) },
                isLoading = uiState is UserSongsUiState.Loading,
                errorMessage = (uiState as? UserSongsUiState.Error)?.message,
                onRetry = { viewModel.loadSongs(searchQuery.takeIf { it.isNotBlank() }) },
                onOpenSong = { song ->
                    navController.navigate(AppsScreens.SongDetailScreen.createRoute(song.id, true))
                },
                onOpenSongs = {
                    navigateFromBottomBar(AppsScreens.LeaderHomeScreen.route)
                },
                onOpenMySongs = {
                    navigateFromBottomBar(AppsScreens.LeaderMySongsScreen.route)
                },
                onOpenTeam = {
                    navigateFromBottomBar(AppsScreens.LeaderTeamScreen.route)
                },
                onOpenInstruments = {
                    navigateFromBottomBar(AppsScreens.LeaderInstrumentsScreen.route)
                },
                onOpenProfile = {
                    navigateFromBottomBar(AppsScreens.LeaderProfileScreen.route)
                }
            )
        }

        composable(route = AppsScreens.LeaderMySongsScreen.route) {
            val viewModel: UserSongsViewModel = viewModel()
            LaunchedEffect(viewModel) {
                viewModel.setMode(UserSongsMode.MY_SONGS)
            }
            val uiState by viewModel.uiState.collectAsState()
            val searchQuery by viewModel.searchQuery.collectAsState()
            val selectedFilter by viewModel.selectedFilter.collectAsState()
            val songs = (uiState as? UserSongsUiState.Success)?.songs.orEmpty()

            UserHomeScreen(
                songs = songs,
                title = "Mis canciones",
                showFilters = true,
                selectedDestination = mx.edu.noisync.ui.components.AuthenticatedDestination.MY_SONGS,
                searchQuery = searchQuery,
                onSearchQueryChange = viewModel::onSearchQueryChange,
                selectedFilter = selectedFilter,
                onShowAll = { viewModel.selectFilter(UserSongsFilter.ALL) },
                onShowRecent = { viewModel.selectFilter(UserSongsFilter.RECENT) },
                onShowPrivate = { viewModel.selectFilter(UserSongsFilter.PRIVATE) },
                isLoading = uiState is UserSongsUiState.Loading,
                errorMessage = (uiState as? UserSongsUiState.Error)?.message,
                onRetry = { viewModel.loadSongs(searchQuery.takeIf { it.isNotBlank() }) },
                onOpenSong = { song ->
                    navController.navigate(AppsScreens.SongDetailScreen.createRoute(song.id, false))
                },
                onOpenSongs = {
                    navigateFromBottomBar(AppsScreens.LeaderHomeScreen.route)
                },
                onOpenMySongs = {
                    navigateFromBottomBar(AppsScreens.LeaderMySongsScreen.route)
                },
                onOpenTeam = {
                    navigateFromBottomBar(AppsScreens.LeaderTeamScreen.route)
                },
                onOpenInstruments = {
                    navigateFromBottomBar(AppsScreens.LeaderInstrumentsScreen.route)
                },
                onOpenProfile = {
                    navigateFromBottomBar(AppsScreens.LeaderProfileScreen.route)
                }
            )
        }

        composable(
            route = AppsScreens.SongDetailScreen.route,
            arguments = listOf(
                navArgument(AppsScreens.SongDetailScreen.ARG_SONG_ID) { type = NavType.StringType },
                navArgument(AppsScreens.SongDetailScreen.ARG_IS_PUBLIC) { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            SongDetailScreen(
                navController = navController,
                songId = backStackEntry.arguments?.getString(AppsScreens.SongDetailScreen.ARG_SONG_ID),
                isPublicSong = backStackEntry.arguments?.getBoolean(AppsScreens.SongDetailScreen.ARG_IS_PUBLIC) == true
            )
        }

        composable(route = AppsScreens.LeaderTeamScreen.route) {
            UserTeamScreen(
                navController = navController,
                onOpenSongs = { navigateFromBottomBar(AppsScreens.LeaderHomeScreen.route) },
                onOpenMySongs = { navigateFromBottomBar(AppsScreens.LeaderMySongsScreen.route) },
                onOpenTeam = { navigateFromBottomBar(AppsScreens.LeaderTeamScreen.route) },
                onOpenInstruments = { navigateFromBottomBar(AppsScreens.LeaderInstrumentsScreen.route) },
                onOpenProfile = { navigateFromBottomBar(AppsScreens.LeaderProfileScreen.route) }
            )
        }

        composable(route = AppsScreens.LeaderInstrumentsScreen.route) {
            UserInstrumentsScreen(
                navController = navController,
                onOpenSongs = { navigateFromBottomBar(AppsScreens.LeaderHomeScreen.route) },
                onOpenMySongs = { navigateFromBottomBar(AppsScreens.LeaderMySongsScreen.route) },
                onOpenTeam = { navigateFromBottomBar(AppsScreens.LeaderTeamScreen.route) },
                onOpenInstruments = { navigateFromBottomBar(AppsScreens.LeaderInstrumentsScreen.route) },
                onOpenProfile = { navigateFromBottomBar(AppsScreens.LeaderProfileScreen.route) }
            )
        }

        composable(route = AppsScreens.LeaderProfileScreen.route) {
            UserInfo(
                navController = navController,
                onOpenSongs = { navigateFromBottomBar(AppsScreens.LeaderHomeScreen.route) },
                onOpenMySongs = { navigateFromBottomBar(AppsScreens.LeaderMySongsScreen.route) },
                onOpenTeam = { navigateFromBottomBar(AppsScreens.LeaderTeamScreen.route) },
                onOpenInstruments = { navigateFromBottomBar(AppsScreens.LeaderInstrumentsScreen.route) },
                onOpenProfile = { navigateFromBottomBar(AppsScreens.LeaderProfileScreen.route) }
            )
        }
    }
}
