package catalog

import org.bson.Document
import catalog.Exchange.Currency;


class User(private val id: Int, val currency: Currency) : Documented {
    override fun doc(): Document? {
        return Document().append("id", id).append("cur", currency.toString())
    }
}