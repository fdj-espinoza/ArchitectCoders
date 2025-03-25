package ing.espinoza.architectcoders.domain.movie.data

import ing.espinoza.architectcoders.domain.movie.entities.Movie

interface MoviesRemoteDataSource {
    suspend fun fetchPopularMovies(region: String): List<Movie>
    suspend fun findMovieById(id: Int): Movie
}