package ing.espinoza.architectcoders.data

import ing.espinoza.architectcoders.domain.movie.data.MoviesLocalDataSource
import ing.espinoza.architectcoders.domain.movie.data.MoviesRemoteDataSource
import ing.espinoza.architectcoders.domain.movie.data.MoviesRepository
import ing.espinoza.architectcoders.domain.movie.entities.Movie
import ing.espinoza.architectcoders.domain.region.data.DEFAULT_REGION
import ing.espinoza.architectcoders.domain.region.data.RegionDataSource
import ing.espinoza.architectcoders.domain.region.data.RegionRepository
import ing.espinoza.architectcoders.sampleMovies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

fun buildMoviesRepositoryWith(
    localData: List<Movie> = emptyList(),
    remoteData: List<Movie> = emptyList()
): MoviesRepository {
    val regionRepository = RegionRepository(FakeRegionDataSource())
    val localDataSource = FakeLocalDataSource().apply { inMemoryMovies.value = localData }
    val remoteDataSource = FakeRemoteDataSource().apply { movies = remoteData }

    return MoviesRepository(
        regionRepository, localDataSource, remoteDataSource
    )
}

class FakeRegionDataSource: RegionDataSource {
    var region: String = DEFAULT_REGION

    override suspend fun findLastRegion(): String = region
}

class FakeLocalDataSource: MoviesLocalDataSource {

    val inMemoryMovies = MutableStateFlow<List<Movie>>(emptyList())

    override val movies: Flow<List<Movie>> = inMemoryMovies

    override fun findMovieById(id: Int): Flow<Movie?> =
        movies.map {
            it.firstOrNull { movie -> movie.id == id }
        }

    override suspend fun saveMovies(movies: List<Movie>) {
        inMemoryMovies.value = movies
    }
}

class FakeRemoteDataSource: MoviesRemoteDataSource {

    var movies = sampleMovies(1, 2, 3, 4)

    override suspend fun fetchPopularMovies(region: String): List<Movie> = movies

    override suspend fun findMovieById(id: Int): Movie = movies.first { it.id == id }
}