package clock

import java.time.Instant

interface Clock {
    fun now(): Instant
}

class ClockSet(var value: Instant) : Clock {
    override fun now(): Instant = value
}