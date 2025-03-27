package ing.espinoza.architectcoders.domain.movie.data

import ing.espinoza.architectcoders.domain.movie.entities.Movie
import ing.espinoza.architectcoders.domain.region.data.DEFAULT_REGION
import ing.espinoza.architectcoders.domain.region.data.RegionRepository
import ing.espinoza.architectcoders.sampleMovie
import ing.espinoza.architectcoders.sampleMovies
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argThat
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryTest {

    @Mock
    lateinit var regionRepository: RegionRepository

    @Mock
    lateinit var localDataSource: MoviesLocalDataSource

    @Mock
    lateinit var remoteDataSource: MoviesRemoteDataSource

    private lateinit var repository: MoviesRepository

    @Before
    fun setUp() {
        repository = MoviesRepository(regionRepository, localDataSource, remoteDataSource)
    }

    @Test
    fun `Popular movies are taken from local data source if available`(): Unit = runBlocking {
        val localMovies = sampleMovies(1, 2)
        whenever(localDataSource.movies).thenReturn(flowOf(localMovies))

        val result = repository.movies

        assertEquals(localMovies, result.first())
    }

    @Test
    fun `Popular movies are saved to local data source when it's empty`(): Unit = runBlocking {
        val localMovies = emptyList<Movie>()
        val remoteMovies = sampleMovies(1, 2)
        whenever(localDataSource.movies).thenReturn(flowOf(localMovies))
        whenever(regionRepository.findLastRegion()).thenReturn(DEFAULT_REGION)
        whenever(remoteDataSource.fetchPopularMovies(DEFAULT_REGION)).thenReturn(remoteMovies)

        repository.movies.first()

        verify(localDataSource).saveMovies(remoteMovies)
    }

    @Test
    fun `Toggling favorite updates local data source`(): Unit = runBlocking {
        val movie = sampleMovie(3)
        repository.toggleFavorite(movie)

        verify(localDataSource).saveMovies(argThat { first().id == 3 })
    }

    @Test
    fun `Switching favorite marks as favorite an un-favorite movie`(): Unit = runBlocking {
        val movie = sampleMovie(1).copy(favorite = false)
        repository.toggleFavorite(movie)

        verify(localDataSource).saveMovies(argThat { first().favorite })
    }

    @Test
    fun `Switching favorite marks as un-favorite a favorite movie`(): Unit = runBlocking {
        val movie = sampleMovie(1).copy(favorite = true)
        repository.toggleFavorite(movie)

        verify(localDataSource).saveMovies(argThat { first().favorite.not() })
    }
}