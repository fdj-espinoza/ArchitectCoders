package ing.espinoza.architectcoders.framework.movie.network

import ing.espinoza.architectcoders.domain.movie.data.MoviesRemoteDataSource
import ing.espinoza.architectcoders.domain.movie.entities.Movie
import org.koin.core.annotation.Factory

@Factory
internal class MoviesServerDataSource(
    private val moviesService: MoviesService
) : MoviesRemoteDataSource {

    override suspend fun fetchPopularMovies(region: String): List<Movie> =
        moviesService
            .fetchPopularMovies(region)
            .results
            .map { it.toDomainModel() }

    override suspend fun findMovieById(id: Int): Movie =
        moviesService
            .fetchMovieById(id)
            .toDomainModel()
}

private fun RemoteMovie.toDomainModel(): Movie =
    Movie(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        poster = posterPath?.let {"https://image.tmdb.org/t/p/w185/$it"},
        backdrop = backdropPath?.let { "https://image.tmdb.org/t/p/w780/$it" },
        originalTitle = originalTitle,
        originalLanguage = originalLanguage,
        popularity = popularity,
        voteAverage = voteAverage,
        favorite = false
    )