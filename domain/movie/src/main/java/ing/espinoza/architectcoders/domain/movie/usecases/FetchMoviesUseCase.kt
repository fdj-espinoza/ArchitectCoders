package ing.espinoza.architectcoders.domain.movie.usecases

import ing.espinoza.architectcoders.domain.movie.data.MoviesRepository
import ing.espinoza.architectcoders.domain.movie.entities.Movie
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class FetchMoviesUseCase(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(): Flow<List<Movie>> = moviesRepository.movies
}