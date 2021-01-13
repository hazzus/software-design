package hashtagapi

import org.junit.Test

class VKClientTest {
    @Test
    fun testSimple() {
        val client = VKClient()
        val manager = VKHashtagManager(client)
        val res = manager.buildStats("собака", 2)
        println(res.getStats())
    }
}