package ing.espinoza.architectcoders.data.datasource

import ing.espinoza.architectcoders.data.Movie
import ing.espinoza.architectcoders.data.datasource.remote.MoviesClient
import ing.espinoza.architectcoders.data.datasource.remote.RemoteMovie

class MoviesRemoteDataSource {

    suspend fun fetchPopularMovies(region: String): List<Movie> =
        MoviesClient
            .instance
            .fetchPopularMovies(region)
            .results
            .map { it.toDomainModel() }

    suspend fun findMovieById(id: Int): Movie =
        MoviesClient
            .instance
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