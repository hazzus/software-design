package clock

import event.ClockEventsStatistic
import event.EventsStatistic
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.Instant

class EventStatisticsTest {
    private lateinit var clock: ClockSet
    private lateinit var stats: EventsStatistic

    @Before
    fun init() {
        clock = ClockSet(Instant.ofEpochSecond(0))
        stats = ClockEventsStatistic(clock)
    }

    @Test
    fun emptyTest() {
        val event = "event"
        Assert.assertEquals(0.0, stats.getEventStatisticByName(event), EPS)
        Assert.assertEquals(0, stats.getAllEventStatistic().size)
    }

    @Test
    fun singleTest() {
        val events = listOf("first", "second")
        stats.incEvent(events[0])
        Assert.assertEquals(0.0, stats.getEventStatisticByName(events[1]), EPS)
        Assert.assertEquals(1, stats.getAllEventStatistic().size)
        Assert.assertEquals(1.0 / 60.0, stats.getEventStatisticByName(events[0]), EPS)
    }

    @Test
    fun multipleTest() {
        val event = "event"
        val count = 500
        for (i in 1 until count) {
            stats.incEvent(event)
            Assert.assertEquals(i / 60.0, stats.getEventStatisticByName(event), EPS)
            Assert.assertEquals(1, stats.getAllEventStatistic().size)
        }
    }

    @Test
    fun hourPassedTest() {
        val event = "event"
        stats.incEvent(event)
        Assert.assertEquals(1.0 / 60.0, stats.getEventStatisticByName(event), EPS)
        clock.value = Instant.ofEpochSecond(5000)
        Assert.assertEquals(0.0, stats.getEventStatisticByName(event), EPS)
    }

    @Test
    fun randomStupidTest() {
        val events = listOf("one", "two", "three")
        val counts = mutableMapOf("one" to 0, "two" to 0, "three" to 0)
        for (i in 1 until 2000) {
            val event = events.random()
            counts[event] = counts.getOrDefault(event, 0) + 1
            stats.incEvent(event)
        }
        for (event in events) {
            Assert.assertEquals(counts[event]?.div(60.0), stats.getEventStatisticByName(event))
        }
    }

    companion object {
        const val EPS = 1e-5
    }
}