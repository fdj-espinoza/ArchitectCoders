package ing.espinoza.architectcoders.ui.navigation

import android.location.Geocoder
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.location.LocationServices
import ing.espinoza.architectcoders.App
import ing.espinoza.architectcoders.BuildConfig
import ing.espinoza.architectcoders.domain.movie.data.MoviesRepository
import ing.espinoza.architectcoders.domain.movie.usecases.FetchMoviesUseCase
import ing.espinoza.architectcoders.domain.movie.usecases.FindMovieByIdUseCase
import ing.espinoza.architectcoders.domain.movie.usecases.ToggleFavoriteUseCase
import ing.espinoza.architectcoders.domain.region.data.RegionRepository
import ing.espinoza.architectcoders.framework.core.MoviesClient
import ing.espinoza.architectcoders.framework.movie.database.MoviesRoomDataSource
import ing.espinoza.architectcoders.framework.movie.network.MoviesServerDataSource
import ing.espinoza.architectcoders.framework.region.GeoCoderRegionDataSource
import ing.espinoza.architectcoders.framework.region.PlayServicesLocationDataSource
import ing.espinoza.architectcoders.ui.detail.DetailScreen
import ing.espinoza.architectcoders.ui.detail.DetailViewlModel
import ing.espinoza.architectcoders.ui.home.HomeScreen
import ing.espinoza.architectcoders.ui.home.HomeViewModel

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
            GeoCoderRegionDataSource(
                Geocoder(app),
                PlayServicesLocationDataSource(LocationServices.getFusedLocationProviderClient(app))
            )
        ),
        MoviesRoomDataSource(app.db.moviesDao()),
        MoviesServerDataSource(
            MoviesClient(
                BuildConfig.TMDB_API_KEY
            ).instance
        )
    )
    NavHost(navController = navController, startDestination = NavScreen.Home.route) {
        composable(NavScreen.Home.route) {
            HomeScreen(
                onMovieClick = { movie ->
                    navController.navigate(NavScreen.Detail.createRoute(movie.id))
                },
                viewModel { HomeViewModel(FetchMoviesUseCase(moviesRepository)) }
            )
        }
        composable(
            NavScreen.Detail.route,
            arguments = listOf(navArgument(NavArgs.MovieId.key) { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = requireNotNull(backStackEntry.arguments?.getInt(NavArgs.MovieId.key))
            DetailScreen(
                viewModel {
                        DetailViewlModel(
                        movieId,
                        FindMovieByIdUseCase(moviesRepository),
                        ToggleFavoriteUseCase(moviesRepository)
                    )
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}