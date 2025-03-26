package ing.espinoza.architectcoders.domain.movie.usecases

import ing.espinoza.architectcoders.domain.movie.data.MoviesRepository
import ing.espinoza.architectcoders.domain.movie.entities.Movie
import kotlinx.coroutines.flow.Flow

class FindMovieByIdUseCase(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(id: Int): Flow<Movie> = moviesRepository.findMovieById(id)
}