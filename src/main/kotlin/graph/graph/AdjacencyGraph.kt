package graph.graph

import graph.api.BaseDrawer
import java.io.InputStreamReader

class AdjacencyGraph(private val drawerApi: BaseDrawer, filePath: String = "adj") : Graph(drawerApi) {
    private val adjMatrix = mutableListOf<List<Int>>()
    private val size: Int
        get() = adjMatrix.size
    init {
        InputStreamReader(javaClass.classLoader.getResourceAsStream(filePath)!!).buffered().useLines { lines ->
            lines.forEach { line ->
                adjMatrix += line.split(" ").map { it.toInt() }
            }
        }
    }

    override fun draw() {
        drawVertices(size)
        (0 until size).forEach { i ->
            (0 until size).forEach { j ->
                if (i < j && adjMatrix[i][j] == 1) {
                    drawEdge(i, j)
                }
            }
        }
        drawerApi.show()
    }
}