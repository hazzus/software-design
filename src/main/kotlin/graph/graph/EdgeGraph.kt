package graph.graph

import graph.api.BaseDrawer
import java.io.InputStreamReader

class EdgeGraph(private val drawerApi: BaseDrawer, filePath: String = "edge") : Graph(drawerApi) {
    private val edges = mutableListOf<Edge>()
    private var size: Int = 0

    init {
        InputStreamReader(javaClass.classLoader.getResourceAsStream(filePath)!!).buffered().useLines { lines ->
            lines.forEachIndexed { i, line ->
                if (i == 0) {
                    size = line.toInt()
                    return@forEachIndexed
                }
                edges += line.split(" ").map { it.toInt() }.let { Edge(it[0] - 1, it[1] - 1) }
            }
        }
    }

    override fun draw() {
        drawVertices(size)
        edges.forEach {
            drawEdge(it.from, it.to)
        }
        drawerApi.show()
    }

    private data class Edge(val from: Int, val to: Int)
}