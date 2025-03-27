package ing.espinoza.architectcoders.ui.detail

import app.cash.turbine.test
import ing.espinoza.architectcoders.data.buildMoviesRepositoryWith
import ing.espinoza.architectcoders.domain.movie.usecases.FindMovieByIdUseCase
import ing.espinoza.architectcoders.domain.movie.usecases.ToggleFavoriteUseCase
import ing.espinoza.architectcoders.sampleMovie
import ing.espinoza.architectcoders.sampleMovies
import ing.espinoza.architectcoders.testrules.CoroutinesTestRule
import ing.espinoza.architectcoders.ui.common.Result
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailIntegrationTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var vm: DetailViewlModel

    @Before
    fun setUp() {
        val moviesRepository = buildMoviesRepositoryWith(localData = sampleMovies(1, 2, 3))
        vm = DetailViewlModel(
            2,
            FindMovieByIdUseCase(moviesRepository),
            ToggleFavoriteUseCase(moviesRepository)
        )
    }

    @Test
    fun `UI is updated with the movie on start`() = runTest {
        vm.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(sampleMovie(2)), awaitItem())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Favorite is updated in local data source`() = runTest {
        vm.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(sampleMovie(2)), awaitItem())

            vm.onFavoriteClicked()
            runCurrent()

            assertEquals(Result.Success(sampleMovie(2).copy(favorite = true)), awaitItem())
        }
    }
}