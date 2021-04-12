package catalog

import io.reactivex.netty.protocol.http.server.HttpServer
import io.reactivex.netty.events.EventSource;

fun main(args: Array<String>) {
    HttpServer
        .newServer(8080)
        .start { req, resp ->
            val name: String = req.decodedPath.substring(1)
            println("NAME: $name")
            val response = Server.process(name, req.queryParameters)
            resp.writeString(response)
        }
        .awaitShutdown()
}