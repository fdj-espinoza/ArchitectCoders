package ing.espinoza.architectcoders.domain.movie.usecases

import ing.espinoza.architectcoders.domain.movie.data.MoviesRepository
import ing.espinoza.architectcoders.sampleMovie
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ToggleFavoriteUseCaseTest {
    @Test
    fun `Invoke calls repository`() = runBlocking {
        val movie = sampleMovie(1)
        val repository = mock<MoviesRepository>()
        val useCase = ToggleFavoriteUseCase(repository)

        useCase(movie)

        verify(repository).toggleFavorite(movie)
    }
}