package ing.espinoza.architectcoders.domain.region.data

import ing.espinoza.architectcoders.domain.region.entities.Location

interface LocationDataSource {
    suspend fun findLastLocation(): Location?
}