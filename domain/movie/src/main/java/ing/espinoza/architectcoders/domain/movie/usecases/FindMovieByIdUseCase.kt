package ing.espinoza.architectcoders.domain.movie.usecases

import ing.espinoza.architectcoders.domain.movie.data.MoviesRepository
import ing.espinoza.architectcoders.domain.movie.entities.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindMovieByIdUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(id: Int): Flow<Movie> = moviesRepository.findMovieById(id)
}