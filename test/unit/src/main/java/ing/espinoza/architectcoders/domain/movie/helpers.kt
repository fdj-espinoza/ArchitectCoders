package ing.espinoza.architectcoders.domain.movie

import ing.espinoza.architectcoders.domain.movie.entities.Movie

fun sampleMovie(id: Int) = Movie(
    id = id,
    title = "Title $id",
    overview = "Overview $id",
    releaseDate = "01/01/2025",
    poster = null,
    backdrop = null,
    originalTitle = "Original Title",
    originalLanguage = "en",
    popularity = 5.0,
    voteAverage = 5.1,
    favorite = false
)

fun sampleMovies(vararg ids: Int) = ids.map { sampleMovie(it) }