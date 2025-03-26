package ing.espinoza.architectcoders.domain.movie.usecases

import ing.espinoza.architectcoders.domain.movie.data.MoviesRepository
import ing.espinoza.architectcoders.domain.movie.entities.Movie
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movie: Movie) = moviesRepository.toggleFavorite(movie)
}