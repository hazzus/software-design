package lru

class LRUCache<K, V>(val capacity: Int) {
    private val cache = hashMapOf<K, Node<K, V>>()
    private var head: Node<K, V>? = null
    private var tail: Node<K, V>? = null

    val size: Int
        get() = cache.size

    init {
        assert(capacity > 0) { "Capacity must be positive" }
    }

    fun put(key : K, value : V) {
        val node = Node(key, value)
        if (key in cache) {
            val oldNode = cache.getValue(key)
            remove(oldNode)
            assert(oldNode.next == null && oldNode.prev == null)
        } else if (size == capacity) {
            tail?.let { tailNode ->
                remove(tailNode)
                assert(tailNode.next == null && tailNode.prev == null)
                cache.remove(tailNode.key)
                assert(size < capacity)
            } ?: error("capacity must be positive")
        }
        cache[key] = node
        toTop(node)
        assert(size <= capacity)
    }

    fun find(key: K): V? {
        if (key !in cache) {
            return null
        }
        val foundNode = cache.getValue(key)
        remove(foundNode)
        toTop(foundNode)
        return foundNode.value
    }

    private fun toTop(node: Node<K, V>) {
        node.next = head
        head?.prev = node
        head = node

        if (size == 1) {
            tail = node
        }
        assert(tail != null)
        assert(head === node)
    }

    private fun remove(node: Node<K, V>) {
        if (node.next == null) {
            tail = node.prev
        }
        if (node.prev == null) {
            head = node.next
        }

        node.prev?.next = node.next
        node.next?.prev = node.prev

        assert(node.prev?.next !== node)
        assert(node.next?.prev !== node)

        node.next = null
        node.prev = null
    }


    private data class Node<K, V>(val key: K, val value: V, var next: Node<K, V>? = null, var prev: Node<K, V>? = null) {
        override fun toString(): String = "key: $key, value: $value"
    }

}