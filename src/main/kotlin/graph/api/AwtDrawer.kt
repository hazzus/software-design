package graph.api

import graph.figure.Circle
import graph.figure.Figure
import graph.figure.Line
import java.awt.Color.*
import java.awt.Frame
import java.awt.Graphics2D
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.geom.Ellipse2D
import kotlin.system.exitProcess

class AwtDrawer : Frame(), BaseDrawer {

    init {
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                super.windowClosing(e)
                exitProcess(0)
            }
        })
        setSize(width(), height())
        isVisible = true
    }

    override fun draw(figure: Figure) {
        val g = graphics as Graphics2D
        if (figure is Circle) {
            g.paint = red
            g.fill(
                Ellipse2D.Float(
                    figure.center.x.toFloat(),
                    figure.center.y.toFloat(),
                    figure.radius.toFloat(),
                    figure.radius.toFloat()
                )
            )
        } else if (figure is Line) {
            g.paint = blue
            g.drawLine(figure.start.x.toInt(), figure.start.y.toInt(), figure.end.x.toInt(), figure.end.y.toInt())
        }
    }
}