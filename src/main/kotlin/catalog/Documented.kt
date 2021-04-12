package catalog

import org.bson.Document

interface Documented {
    fun doc(): Document?
}