package ing.espinoza.architectcoders.ui.detail

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ing.espinoza.architectcoders.ui.common.NavArgs
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class DetailViewlModelModule {

    @Provides
    @ViewModelScoped
    @Named("movieId")
    fun provideMovieId(savedStateHandle: SavedStateHandle): Int {
        return savedStateHandle[NavArgs.MovieId.key]
            ?: throw IllegalArgumentException("MovieId is required")
    }
}