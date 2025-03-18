package ing.espinoza.architectcoders.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ing.espinoza.architectcoders.data.Movie
import ing.espinoza.architectcoders.data.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewlModel(private val id: Int): ViewModel() {

    private val repository = MoviesRepository()

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    /*
    //CHANNELS
    private val _events = Channel<UiEvent>()
    val events: Flow<UiEvent> = _events.receiveAsFlow()
    */

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(loading = false, movie = repository.findMovieById(id))
        }
    }

    //MVI
    fun onAction(action: DetailAction) {
        when (action) {
            is DetailAction.FavoriteClick -> _state.update { it.copy(message = "Favorite clicked") }
            is DetailAction.MessageShown ->  _state.update { it.copy(message = null) }
        }
    }

    /*
    //STATE
    fun onFavoriteClick() {
        //_events.trySend(UiEvent.ShowMessage("Favorite clicked"))
        _state.update { it.copy(message = "Favorite clicked") }
    }

    fun onMessageShown() {
        _state.update { it.copy(message = null) }
    }*/

    data class UiState(
        val loading: Boolean = false,
        val movie: Movie? = null,
        val message: String? = null
    )

    /*
    //CHANNELS
    sealed interface UiEvent {
        data class ShowMessage(val message: String): UiEvent
    }
    */


    sealed interface DetailAction {
        data object FavoriteClick: DetailAction
        data object MessageShown: DetailAction
        //data class MovieClick(val movie: Movie): DetailAction
    }
}