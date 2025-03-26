package ing.espinoza.architectcoders.framework.region

import android.annotation.SuppressLint
import android.location.Location as AndroidLocation
import com.google.android.gms.location.FusedLocationProviderClient
import ing.espinoza.architectcoders.domain.region.data.LocationDataSource
import ing.espinoza.architectcoders.domain.region.entities.Location
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.core.annotation.Factory
import kotlin.coroutines.resume

@Factory
internal class PlayServicesLocationDataSource(
    private val fusedLocationClient: FusedLocationProviderClient
) : LocationDataSource {

    override suspend fun findLastLocation(): Location? = fusedLocationClient.lastLocation()

}

@SuppressLint("MissingPermission")
private suspend fun FusedLocationProviderClient.lastLocation(): Location? {
    return suspendCancellableCoroutine { continuation ->
        lastLocation.addOnSuccessListener { location ->
            continuation.resume(location.toDomainLocation())
        }.addOnFailureListener {
            continuation.resume(null)
        }
    }
}

private fun AndroidLocation.toDomainLocation() = Location(latitude, longitude)