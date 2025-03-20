package ing.espinoza.architectcoders.ui.screens

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ing.espinoza.architectcoders.App
import ing.espinoza.architectcoders.data.MoviesRepository
import ing.espinoza.architectcoders.data.RegionRepository
import ing.espinoza.architectcoders.data.datasource.LocationDataSource
import ing.espinoza.architectcoders.data.datasource.MoviesLocalDataSource
import ing.espinoza.architectcoders.data.datasource.MoviesRemoteDataSource
import ing.espinoza.architectcoders.data.datasource.RegionDataSource
import ing.espinoza.architectcoders.ui.screens.detail.DetailScreen
import ing.espinoza.architectcoders.ui.screens.detail.DetailViewlModel
import ing.espinoza.architectcoders.ui.screens.home.HomeScreen
import ing.espinoza.architectcoders.ui.screens.home.HomeViewModel

sealed class NavScreen(val route: String) {
    data object Home : NavScreen("home")
    data object Detail : NavScreen("detail/{${NavArgs.MovieId.key}}") {
        fun createRoute(movieId: Int) = "detail/$movieId"
    }
}

enum class NavArgs(val key: String) {
    MovieId("movieId")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val app = LocalContext.current.applicationContext as App
    val moviesRepository = MoviesRepository(
        RegionRepository(
            RegionDataSource(app, LocationDataSource(app))
        ),
        MoviesLocalDataSource(app.db.moviesDao()),
        MoviesRemoteDataSource()
    )
    NavHost(navController = navController, startDestination = NavScreen.Home.route) {
        composable(NavScreen.Home.route) {
            HomeScreen(
                onMovieClick = { movie ->
                    navController.navigate(NavScreen.Detail.createRoute(movie.id))
                },
                viewModel { HomeViewModel(moviesRepository) }
            )
        }
        composable(
            NavScreen.Detail.route,
            arguments = listOf(navArgument(NavArgs.MovieId.key) { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = requireNotNull(backStackEntry.arguments?.getInt(NavArgs.MovieId.key))
            DetailScreen(
                viewModel { DetailViewlModel(movieId, moviesRepository) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}