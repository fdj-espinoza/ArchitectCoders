package ing.espinoza.architectcoders.framework.core

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object FrameworkCoreModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(app, MoviesDatabase::class.java, "movies-db").build()

    @Provides
    fun provideMoviesDao(db: MoviesDatabase) = db.moviesDao()

    @Provides
    @Singleton
    fun provideMoviesClient(@Named("apiKey") apiKey: String) = MoviesClient(apiKey).instance
}