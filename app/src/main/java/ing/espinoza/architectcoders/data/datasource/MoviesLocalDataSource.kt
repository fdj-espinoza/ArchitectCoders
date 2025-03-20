package ing.espinoza.architectcoders.data.datasource

import ing.espinoza.architectcoders.data.Movie
import ing.espinoza.architectcoders.data.datasource.database.MoviesDao

class MoviesLocalDataSource(
    private val moviesDao: MoviesDao
) {
    val movies = moviesDao.fetchPopularMovies()

    fun findMovieById(id: Int) = moviesDao.findMovieById(id)

    suspend fun isEmpty(): Boolean = moviesDao.countMovies() == 0

    suspend fun saveMovies(movies: List<Movie>) = moviesDao.saveMovies(movies)
}