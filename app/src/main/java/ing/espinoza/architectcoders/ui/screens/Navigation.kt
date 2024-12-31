package ing.espinoza.architectcoders.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ing.espinoza.architectcoders.ui.screens.detail.DetailScreen
import ing.espinoza.architectcoders.ui.screens.detail.DetailViewlModel
import ing.espinoza.architectcoders.ui.screens.home.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(onClick = { movie ->
                navController.navigate("detail/${movie.id}")
            })
        }
        composable(
            "detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = requireNotNull(backStackEntry.arguments?.getInt("movieId"))
            DetailScreen(
                vm = viewModel { DetailViewlModel(movieId) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}