package hashtagapi

import com.nhaarman.mockitokotlin2.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.junit.Assert
import org.mockito.MockitoAnnotations
import org.junit.Before
import org.junit.Test


class VKHashtagManagerTest {
    private val client: Client = mock()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getStatsTest() {
        val manager = VKHashtagManager(client)
        val startTime = getHourStartTime(5);
        whenever(client.getData(any(), any(), isNull())).thenReturn(mockingAnswer(startTime))
        val stat = manager.buildStats("собака", 5)
        Assert.assertEquals(listOf<Int>(2, 1, 1, 0, 0), stat.getStats());
    }

    private fun mockingAnswer(startTime: Long): JsonObject  {
        return Json.decodeFromString(
            """
                {
                    "items": [
                        { "date": ${startTime + 60} },
                        { "date": ${startTime + 100} },
                        { "date": ${startTime + 3660} },
                        { "date": ${startTime + 3660 * 2} }
                    ]
                }
            """.trimIndent()
        )
    }
}