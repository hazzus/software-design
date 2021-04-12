package graph.api

import graph.figure.Figure

interface BaseDrawer {
    fun width(): Int = 900
    fun height(): Int = 900
    fun draw(figure: Figure)
    fun show()
}