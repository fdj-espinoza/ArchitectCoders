package ing.espinoza.architectcoders.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ing.espinoza.architectcoders.ui.common.NavArgs
import ing.espinoza.architectcoders.ui.common.NavScreen
import ing.espinoza.architectcoders.ui.detail.DetailScreen
import ing.espinoza.architectcoders.ui.home.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavScreen.Home.route) {
        composable(NavScreen.Home.route) {
            HomeScreen(
                onMovieClick = { movie ->
                    navController.navigate(NavScreen.Detail.createRoute(movie.id))
                }
            )
        }
        composable(
            NavScreen.Detail.route,
            arguments = listOf(navArgument(NavArgs.MovieId.key) { type = NavType.IntType })
        ) { backStackEntry ->
            DetailScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}