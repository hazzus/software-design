package hashtagapi

import kotlinx.serialization.json.*

class VKHashtagStat(private val startTime: Long, private val period: Int) {
    private val stats = MutableList(period) { 0 }
    private val timestamps = mutableListOf<Long>()

    fun update(items: List<HashtagInfo>) {
        for (i in items) {
            val timestamp = i.timestamp
            timestamps.add(0, timestamp)
            val hourDifference = ((timestamp - startTime) / 3600).toInt()
            assert(hourDifference < period)
            stats[hourDifference]++
        }
    }

    fun getStats(): List<Int> {
        return stats
    }

}