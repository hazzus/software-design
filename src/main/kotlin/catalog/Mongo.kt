package catalog

import catalog.Exchange.fromString
import com.mongodb.client.model.Filters
import com.mongodb.rx.client.MongoClients
import com.mongodb.rx.client.Success
import org.bson.BsonDocument
import org.bson.Document
import org.bson.conversions.Bson
import rx.Observable
import rx.functions.Func1


object Mongo {
    private val client = MongoClients.create()
    private val emptyFilter: Bson = BsonDocument()
    private const val DATABASE_NAME = "products"
    private const val USER_COLLECTION = "user"
    private const val PRODUCT_COLLECTION = "product"

    private fun <T : Documented?> insert(collection: String, doc: T): Observable<Success> {
        return client.getDatabase(DATABASE_NAME)
            .getCollection(collection)
            .insertOne(doc!!.doc())
    }

    private operator fun <R> get(collection: String, filter: Bson, function: Func1<in Document, out R>): Observable<R> {
        return client.getDatabase(DATABASE_NAME)
            .getCollection(collection)
            .find(filter).toObservable().map(function)
    }

    fun addUser(user: User): Observable<Success> {
        return insert(USER_COLLECTION, user)
    }

    fun addProduct(product: Product): Observable<Success> {
        return insert<Documented>(PRODUCT_COLLECTION, product)
    }

    val products: Observable<Product>
        get() = get(
            PRODUCT_COLLECTION, emptyFilter
        ) { doc: Document ->
            Product(
                doc.getString("name"),
                doc.getDouble("price")
            )
        }

    fun getUser(id: Int): Observable<User> {
        return get(
            USER_COLLECTION, Filters.eq("id", id)
        ) { doc: Document ->
            User(
                doc.getInteger("id"),
                fromString(doc.getString("cur"))
            )
        }
    }
}