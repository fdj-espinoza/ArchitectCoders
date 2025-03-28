package ing.espinoza.architectcoders.framework.core

import androidx.room.Database
import androidx.room.RoomDatabase
import ing.espinoza.architectcoders.framework.movie.database.DbMovie
import ing.espinoza.architectcoders.framework.movie.database.MoviesDao

@Database(entities = [DbMovie::class], version = 1, exportSchema = false)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}