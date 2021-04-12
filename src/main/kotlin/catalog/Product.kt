package catalog

import org.bson.Document

class Product(private val name: String, val priceInRub: Double) : Documented {
    override fun doc(): Document? {
        return Document().append("name", name).append("price", priceInRub)
    }
}