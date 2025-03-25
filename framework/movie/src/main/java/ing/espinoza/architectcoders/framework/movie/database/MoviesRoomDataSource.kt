package ing.espinoza.architectcoders.framework.movie.database

import ing.espinoza.architectcoders.domain.movie.data.MoviesLocalDataSource
import ing.espinoza.architectcoders.domain.movie.entities.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRoomDataSource(
    private val moviesDao: MoviesDao
) : MoviesLocalDataSource {
    override val movies: Flow<List<Movie>> =
        moviesDao.fetchPopularMovies()
            .map { it.map { it.toDomainMovie() } }

    override fun findMovieById(id: Int): Flow<Movie?> =
        moviesDao.findMovieById(id)
            .map { it?.toDomainMovie() }

    override suspend fun saveMovies(movies: List<Movie>) = moviesDao.saveMovies(movies.map { it.toDbMovie() })
}


private fun DbMovie.toDomainMovie(): Movie = Movie(
    id = id,
    title = title,
    overview = overview,
    releaseDate = releaseDate,
    poster = poster,
    backdrop = backdrop,
    originalTitle = originalTitle,
    originalLanguage = originalLanguage,
    popularity = popularity,
    voteAverage = voteAverage,
    favorite = favorite
)

private fun Movie.toDbMovie(): DbMovie = DbMovie(
    id = id,
    title = title,
    overview = overview,
    releaseDate = releaseDate,
    poster = poster,
    backdrop = backdrop,
    originalTitle = originalTitle,
    originalLanguage = originalLanguage,
    popularity = popularity,
    voteAverage = voteAverage,
    favorite = favorite
)