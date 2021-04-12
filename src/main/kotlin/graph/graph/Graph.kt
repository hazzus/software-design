package graph.graph

import graph.api.BaseDrawer
import graph.figure.Circle
import graph.figure.Line
import graph.figure.Point
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

abstract class Graph(private val drawer: BaseDrawer) {
    private val points = mutableMapOf<Int, Point>()

    abstract fun draw()

    fun drawVertices(verticesCount: Int) {
        val multiplier = (drawer.width() + drawer.height()) / 6
        val radius = 20.0
        val wOffset = drawer.width() / 2
        val hOffset = drawer.height() / 2
        val step = 2 * PI / verticesCount
        (0 until verticesCount).forEach {
            val x = (multiplier * cos(2 * PI - it * step) + wOffset)
            val y = (multiplier * sin(2 * PI - it * step) + hOffset)
            points += it to Point(x + radius / 2, y + radius / 2)
            drawer.draw(Circle(Point(x, y), radius))
        }
    }

    fun drawEdge(from: Int, to: Int) {
        val fromPoint = points.getValue(from)
        val toPoint = points.getValue(to)
        drawer.draw(Line(fromPoint, toPoint))
    }
}