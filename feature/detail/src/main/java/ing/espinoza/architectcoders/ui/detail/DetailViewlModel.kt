package ing.espinoza.architectcoders.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ing.espinoza.architectcoders.domain.movie.entities.Movie
import ing.espinoza.architectcoders.domain.movie.usecases.FindMovieByIdUseCase
import ing.espinoza.architectcoders.domain.movie.usecases.ToggleFavoriteUseCase
import ing.espinoza.architectcoders.ui.common.ifSuccess
import ing.espinoza.architectcoders.ui.common.stateAsResultIn
import ing.espinoza.architectcoders.ui.common.Result
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class DetailViewlModel @Inject constructor(
    @Named("movieId") id: Int,
    findMovieByIdUseCase: FindMovieByIdUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
): ViewModel() {

    val state: StateFlow<Result<Movie>> = findMovieByIdUseCase(id)
        .stateAsResultIn(viewModelScope)

    fun onFavoriteClicked() {
        state.value.ifSuccess { movie ->
            viewModelScope.launch {
                toggleFavoriteUseCase(movie)
            }
        }
    }
}