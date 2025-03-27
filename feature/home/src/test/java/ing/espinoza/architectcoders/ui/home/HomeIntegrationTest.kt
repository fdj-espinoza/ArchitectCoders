package ing.espinoza.architectcoders.ui.home

import app.cash.turbine.test
import ing.espinoza.architectcoders.data.buildMoviesRepositoryWith
import ing.espinoza.architectcoders.domain.movie.entities.Movie
import ing.espinoza.architectcoders.domain.movie.usecases.FetchMoviesUseCase
import ing.espinoza.architectcoders.sampleMovies
import ing.espinoza.architectcoders.testrules.CoroutinesTestRule
import ing.espinoza.architectcoders.ui.common.Result
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class HomeIntegrationTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `Data is loaded from server when local data source is empty`() = runTest{
        val remoteData = sampleMovies(1, 2)
        val vm = buildViewModelWith(remoteData = remoteData)

        vm.onUiReady()

        vm.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(emptyList<Movie>()), awaitItem())
            assertEquals(Result.Success(remoteData), awaitItem())
        }
    }

    @Test
    fun `Data is loaded from local source when available`() = runTest{
        val localData = sampleMovies(1, 2)
        val vm = buildViewModelWith(localData = localData)

        vm.onUiReady()

        vm.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(localData), awaitItem())
        }
    }

    private fun buildViewModelWith(
        localData: List<Movie> = emptyList(),
        remoteData: List<Movie> = emptyList()
    ) = HomeViewModel(
        FetchMoviesUseCase(buildMoviesRepositoryWith(localData, remoteData))
    )
}