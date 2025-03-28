package ing.espinoza.architectcoders.domain.region.data

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class RegionRepositoryTest {

    @Test
    fun `findLastRegion calls RegionDataSource`(): Unit = runBlocking {
        val repository = RegionRepository(mock {
            onBlocking { findLastRegion() } doReturn "ES"
        })

        val region = repository.findLastRegion()

        assertEquals("ES", region)
    }
}