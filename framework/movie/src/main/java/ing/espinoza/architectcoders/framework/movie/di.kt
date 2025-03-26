package ing.espinoza.architectcoders.framework.movie

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ing.espinoza.architectcoders.domain.movie.data.MoviesLocalDataSource
import ing.espinoza.architectcoders.domain.movie.data.MoviesRemoteDataSource
import ing.espinoza.architectcoders.framework.movie.database.MoviesRoomDataSource
import ing.espinoza.architectcoders.framework.movie.network.MoviesServerDataSource

@Module
@InstallIn(SingletonComponent::class)
internal abstract class FrameworkMovieModule {
    @Binds
    abstract fun bindLocalDataSource(localDataSource: MoviesRoomDataSource): MoviesLocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: MoviesServerDataSource): MoviesRemoteDataSource
}