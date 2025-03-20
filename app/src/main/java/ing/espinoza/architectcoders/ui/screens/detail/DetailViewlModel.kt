package ing.espinoza.architectcoders.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ing.espinoza.architectcoders.data.Movie
import ing.espinoza.architectcoders.data.MoviesRepository
import ing.espinoza.architectcoders.Result
import ing.espinoza.architectcoders.ifSuccess
import ing.espinoza.architectcoders.stateAsResultIn
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewlModel(
    id: Int,
    private val repository: MoviesRepository
): ViewModel() {

    val state: StateFlow<Result<Movie>> = repository.findMovieById(id)
        .stateAsResultIn(viewModelScope)

    fun onFavoriteClicked() {
        state.value.ifSuccess { movie ->
            viewModelScope.launch {
                repository.toggleFavorite(movie)
            }
        }
    }
}