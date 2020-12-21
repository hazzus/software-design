package hashtagapi

import kotlinx.serialization.json.jsonPrimitive
import java.time.Instant

fun getHourStartTime(hoursPeriod: Int): Long {
    val time = Instant.now().epochSecond
    return (time - time % 3600) - (hoursPeriod.toLong() - 1) * 3600
}

class VKHashtagManager(private val client: Client) {
    fun buildStats(hashtag: String, hoursPeriod: Int): VKHashtagStat {
        val startTime = getHourStartTime(hoursPeriod)
        val stat = VKHashtagStat(startTime, hoursPeriod)
        var continueMark: String? = null
        while (true) {
            val response = client.getData(hashtag, startTime, continueMark)
            stat.update(response)
            if ("next_from" !in response) {
                break
            }
            continueMark = response["next_from"]!!.jsonPrimitive.toString()
        }
        return stat
    }
}