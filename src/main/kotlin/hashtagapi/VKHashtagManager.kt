package hashtagapi

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
            val result = client.getData(hashtag, startTime, continueMark)
            stat.update(result.first)
            if (result.second is String) {
                continueMark = result.second
            } else {
                break
            }
        }
        return stat
    }
}