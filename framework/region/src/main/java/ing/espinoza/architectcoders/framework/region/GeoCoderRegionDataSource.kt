package ing.espinoza.architectcoders.framework.region

import android.location.Geocoder
import ing.espinoza.architectcoders.domain.region.data.DEFAULT_REGION
import ing.espinoza.architectcoders.domain.region.data.LocationDataSource
import ing.espinoza.architectcoders.domain.region.data.RegionDataSource
import ing.espinoza.architectcoders.domain.region.entities.Location
import javax.inject.Inject

internal class GeoCoderRegionDataSource @Inject constructor(
    private val geocoder: Geocoder,
    private val locationDataSource: LocationDataSource
) : RegionDataSource {

    override suspend fun findLastRegion(): String =
        locationDataSource.findLastLocation()?.toRegion() ?: DEFAULT_REGION

    private suspend fun Location.toRegion(): String {
        val addresses = geocoder.getFromLocationCompat(latitude, longitude, 1)
        val region = addresses.firstOrNull()?.countryCode
        return region ?: DEFAULT_REGION
    }
}