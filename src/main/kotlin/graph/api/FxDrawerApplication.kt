package graph.api

import graph.figure.Circle
import graph.figure.Figure
import graph.figure.Line

import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import javafx.stage.Stage

class FxDrawer(
    private val lines: MutableList<Line> = mutableListOf(),
    private val circles: MutableList<Circle> = mutableListOf()
) : BaseDrawer {

    override fun draw(figure: Figure) {
        if (figure is Circle) {
            circles += figure
        } else if (figure is Line) {
            lines += figure
        }
    }

    override fun show() {
        FxDrawerApplication.initialize(lines, circles, width(), height())
        Application.launch(FxDrawerApplication::class.java)
    }
}

class FxDrawerApplication : Application() {
    override fun start(primaryStage: Stage) {
        val root = Group()
        val canvas = Canvas(width.toDouble(), height.toDouble())
        val gc = canvas.graphicsContext2D
        root.children += canvas
        gc.fill = Color.BLUE
        circles.forEach { gc.fillOval(
            it.center.x.toDouble(),
            it.center.y.toDouble(),
            it.radius.toDouble(),
            it.radius.toDouble()
        ) }

        gc.stroke = Color.GREEN
        lines.forEach { gc.strokeLine(
            it.start.x.toDouble(),
            it.start.y.toDouble(),
            it.end.x.toDouble(),
            it.end.y.toDouble()
        ) }

        primaryStage.scene = Scene(root)
        primaryStage.show()
    }

    companion object {
        private var lines: List<Line> = mutableListOf()
        private var circles: List<Circle> = mutableListOf()
        private var width: Int = 0
        private var height: Int = 0

        fun initialize(lines: List<Line>, circles: List<Circle>, width: Int, height: Int) {
            FxDrawerApplication.lines = lines
            FxDrawerApplication.circles = circles
            FxDrawerApplication.width = width
            FxDrawerApplication.height = height
        }
    }
}