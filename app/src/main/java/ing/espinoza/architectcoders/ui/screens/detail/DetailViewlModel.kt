package ing.espinoza.architectcoders.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ing.espinoza.architectcoders.data.Movie
import ing.espinoza.architectcoders.data.MoviesClient
import ing.espinoza.architectcoders.data.MoviesRepository
import kotlinx.coroutines.launch

class DetailViewlModel(private val id: Int): ViewModel() {

    private val repository = MoviesRepository(MoviesClient.instance)

    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(loading = true)
            state = UiState(loading = false, movie = repository.findMovieById(id))
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movie: Movie? = null
    )
}