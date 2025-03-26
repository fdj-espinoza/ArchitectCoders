package ing.espinoza.architectcoders.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ing.espinoza.architectcoders.domain.movie.entities.Movie
import ing.espinoza.architectcoders.domain.movie.usecases.FetchMoviesUseCase
import ing.espinoza.architectcoders.ui.common.Result
import ing.espinoza.architectcoders.ui.common.stateAsResultIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchMoviesUseCase: FetchMoviesUseCase
) : ViewModel() {

    private val uiReady = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<Result<List<Movie>>> = uiReady
        .filter { it }
        .flatMapLatest { fetchMoviesUseCase() }
        .stateAsResultIn(viewModelScope)

    fun onUiReady() {
        uiReady.value = true
    }
}