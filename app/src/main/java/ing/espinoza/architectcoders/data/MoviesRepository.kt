package ing.espinoza.architectcoders.data

class MoviesRepository(
    private val moviesService: MoviesService
) {

    //Para que se ejecute en un hilo secundario
    suspend fun fetchPopularMovies(region: String): List<Movie> =
        moviesService
            .fetchPopularMovies(region)
            .results
            .map { it.toDomainModel() }

    suspend fun findMovieById(id: Int): Movie =
        moviesService
            .fetchMovieById(id)
            .toDomainModel()
}

private fun RemoteMovie.toDomainModel(): Movie =
    Movie(
        id = id ?: 0,
        title = title ?: "",
        poster = "https://image.tmdb.org/t/p/w185/$posterPath"
    )