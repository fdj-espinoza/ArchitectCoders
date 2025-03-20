package ing.espinoza.architectcoders.data

import ing.espinoza.architectcoders.data.datasource.RegionDataSource

class RegionRepository(private val regionDataSource: RegionDataSource) {

    suspend fun findLastRegion(): String = regionDataSource.findLastRegion()

}