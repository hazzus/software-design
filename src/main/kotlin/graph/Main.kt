package graph

import graph.api.AwtDrawer
import graph.api.FxDrawer
import graph.graph.AdjacencyGraph
import graph.graph.EdgeGraph

fun main(args: Array<String>) {
    require(args.size == 2) { "Please provide two arguments with api and representation" }

    val drawer = when (args[0]) {
        "awt" -> AwtDrawer()
        "fx"  -> FxDrawer()
        else  -> error("Unknown drawer ${args[0]}, please use awt or fx")
    }

    val graph = when (args[1]) {
        "adj"  -> AdjacencyGraph(drawer)
        "edge" -> EdgeGraph(drawer)
        else   -> error("Unknown graph type ${args[1]}, please use adj or edge")
    }

    graph.draw()
}