package mx.edu.noisync.ui.navigation

sealed class AppsScreens(val route: String){
    object SongDetailScreen: AppsScreens("first_screen")
    object VisitorHomeScreen: AppsScreens("second_screen")

    object LoginScreen: AppsScreens("third_screen")
}