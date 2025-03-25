package ing.espinoza.architectcoders.domain.movie.data

import ing.espinoza.architectcoders.domain.movie.entities.Movie
import ing.espinoza.architectcoders.domain.region.data.RegionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach

class MoviesRepository(
    private val regionRepository: RegionRepository,
    private val localDataSource: MoviesLocalDataSource,
    private val remoteDataSource: MoviesRemoteDataSource
) {

    val movies: Flow<List<Movie>> = localDataSource.movies.onEach { localMovies ->
        if (localMovies.isEmpty()) {
            val region = regionRepository.findLastRegion()
            val movies = remoteDataSource.fetchPopularMovies(region)
            localDataSource.saveMovies(movies)
        }
    }

    fun findMovieById(id: Int): Flow<Movie> = localDataSource.findMovieById(id)
        .onEach { localMovie ->
            if (localMovie == null) {
                val remoteMovie =  remoteDataSource.findMovieById(id)
                localDataSource.saveMovies(listOf(remoteMovie))
            }
        }
        .filterNotNull()

    suspend fun toggleFavorite(movie: Movie) {
        localDataSource.saveMovies(listOf(movie.copy(favorite = !movie.favorite)))
    }
}