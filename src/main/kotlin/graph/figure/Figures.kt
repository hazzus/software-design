package graph.figure

open class Figure

class Point(val x: Double, val y: Double) : Figure()

class Circle(val center: Point, val radius: Double) : Figure()

class Line(val start: Point, val end: Point) : Figure()