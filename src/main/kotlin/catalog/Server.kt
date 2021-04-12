package catalog

import catalog.Exchange.convertFrom
import catalog.Exchange.convertTo
import catalog.Exchange.fromString
import catalog.Mongo.addProduct
import catalog.Mongo.addUser
import catalog.Mongo.getUser
import catalog.Mongo.products
import com.sun.net.httpserver.HttpServer
import rx.Observable


object Server {

    fun process(str: String, queryParameters: Map<String?, List<String>>): Observable<String> {
        return try {
            when (str) {
                "register" -> register(queryParameters)
                "add" -> add(queryParameters)
                "list" -> list(queryParameters)
                else -> Observable.just("$str no such command!")
            }
        } catch (e: Exception) {
            Observable.just("Invalid arguments")
        }
    }

    fun list(queryParameters: Map<String?, List<String>>): Observable<String> {
        val id = queryParameters["id"]!![0].toInt()
        return getUser(id).map(User::currency)
            .flatMap { currency ->
                products
                    .map { product ->
                        convertTo(
                            product.priceInRub,
                            currency
                        ).toString() + System.lineSeparator()
                    }
            }
    }

    fun add(queryParameters: Map<String?, List<String>>): Observable<String> {
        val name = queryParameters["name"]!![0]
        val value = convertFrom(
            queryParameters["value"]!![0].toDouble(),
            fromString(queryParameters["currency"]!![0])
        )
        addProduct(Product(name, value))
        return Observable.just("Item added")
    }

    fun register(queryParameters: Map<String?, List<String>>): Observable<String> {
        val id = queryParameters["id"]!![0].toInt()
        val currency = fromString(queryParameters["currency"]!![0])
        addUser(User(id, currency))
        return Observable.just("User registered")
    }
}