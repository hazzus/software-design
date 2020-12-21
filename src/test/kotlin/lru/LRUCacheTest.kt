package lru

import java.util.LinkedHashMap
import java.util.Random
import org.junit.Assert
import org.junit.Test


class LRUCacheTest {
    class StupidCache<K, V>(private val capacity: Int) : LinkedHashMap<K, V>(capacity, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?): Boolean {
            return size > capacity
        }
    }

    @Test
    fun testSimpleSingleHit() {
        val cache = LRUCache<Int, Double>(1)
        Assert.assertEquals(1, cache.capacity)
        Assert.assertEquals(0, cache.size)
        Assert.assertNull(cache.find(29))

        cache.put(42, 4.2)
        Assert.assertEquals(1, cache.size)
        Assert.assertEquals(4.2, cache.find(42))
    }

    @Test
    fun testSimpleSingleDisplace() {
        val cache = LRUCache<Int, Double>(1)
        Assert.assertEquals(1, cache.capacity)
        Assert.assertEquals(0, cache.size)
        Assert.assertNull(cache.find(29))

        cache.put(29, 4.2)
        Assert.assertEquals(1, cache.size)
        Assert.assertEquals(4.2, cache.find(29))

        cache.put(28, 3.2)
        Assert.assertNull(cache.find(29))
        Assert.assertEquals(3.2, cache.find(28))
    }

    @Test
    fun testSimpleSingleReassign() {
        val cache = LRUCache<Int, Double>(1)
        Assert.assertEquals(1, cache.capacity)
        Assert.assertEquals(0, cache.size)

        cache.put(0, 3.2)
        Assert.assertEquals(1, cache.size)
        Assert.assertEquals(3.2, cache.find(0))

        cache.put(0, 4.2)
        Assert.assertEquals(1, cache.size)
        Assert.assertEquals(4.2, cache.find(0))
    }

    @Test
    fun testSimpleMulti() {
        val cache = LRUCache<Int, Double>(20)
        cache.put(1, 2.0)
        cache.put(2, 3.0)
        cache.put(4, 4.2)
        cache.put(-100000, 1.3e-8)
        cache.put(-52134, 4.45e+100)
        Assert.assertEquals(5, cache.size)
        Assert.assertNull(cache.find(5))

        Assert.assertEquals(2.0, cache.find(1))
        Assert.assertEquals(4.2, cache.find(4))
        Assert.assertEquals(1.3e-8, cache.find(-100000))

        Assert.assertEquals(3.0, cache.find(2))
        cache.put(2, 3.333)
        Assert.assertEquals(3.333, cache.find(2))
    }

    @Test
    fun testMultipleDisplace() {
        val cache = LRUCache<Int, Int>(4)
        cache.put(1, 42)
        Assert.assertEquals(1, cache.size)
        Assert.assertNull(cache.find(2))
        cache.put(2, 256)
        cache.put(-42, -42)
        cache.put(0, -1000)
        Assert.assertEquals(4, cache.size)
        cache.put(-4242, 666)
        Assert.assertEquals(4, cache.size)
        Assert.assertNull(cache.find(1))
        Assert.assertEquals(256, cache.find(2))
        cache.put(3, 100500)
        Assert.assertNull(cache.find(-42))
    }

    @Test
    fun testRandomized() {
        val cache = LRUCache<Int, Int>(30)
        val expected = StupidCache<Int, Int>(30)

        val rand = Random()

        val keys = HashSet<Int>()
        for (i in 0..200) {
            keys.add(rand.nextInt())
        }

        val insertionChance = 0.8
        for (i in 0..1000000) {
            val key = keys.random()
            val insertionScore = rand.nextDouble()
            if (insertionScore <= insertionChance) {
                val value = rand.nextInt()
                cache.put(key, value)
                expected[key] = value
            } else {
                Assert.assertEquals(expected[key], cache.find(key))
            }
            Assert.assertEquals(expected.size, cache.size)
        }
    }
}