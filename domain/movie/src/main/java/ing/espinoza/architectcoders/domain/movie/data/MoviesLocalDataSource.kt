package ing.espinoza.architectcoders.domain.movie.data

import ing.espinoza.architectcoders.domain.movie.entities.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesLocalDataSource {
    val movies: Flow<List<Movie>>
    fun findMovieById(id: Int): Flow<Movie?>
    suspend fun saveMovies(movies: List<Movie>)
}