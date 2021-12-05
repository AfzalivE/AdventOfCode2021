private const val DEBUG = false

data class Coordinate(
    val x: Int,
    val y: Int
) {
    companion object {
        fun fromInputList(input: List<String>, diagonals: Boolean = false, onParsedCoordinate: (Coordinate) -> Unit): List<Coordinate> {
            return input.map { row -> 
                val (coord1, coord2) = row.split("->").map { coordinates ->
                    val coord = Coordinate.fromString(coordinates)
                    onParsedCoordinate(coord)
                    coord
                }
                
                val coordinatesBetween = coord1.coordinatesBetween(coord2, diagonals)
                debug(coordinatesBetween)
                coordinatesBetween
            }.flatten()
        }
        fun fromString(input: String): Coordinate {
            val (x, y) = input.trim().split(",").map { it.toInt() }
            return Coordinate(x, y)
        }
    }

    fun coordinatesBetween(other: Coordinate, diagonals: Boolean = false): List<Coordinate> {
        return if (x == other.x) {
            if (y < other.y) {
                (y .. other.y).map { it ->
                    Coordinate(x, it)
                }
            } else {
                (other.y .. y).map { it ->
                    Coordinate(x, it)
                }
            }
        } else if (y == other.y) {
            if (x < other.x) {
                (x .. other.x).map { it ->
                    Coordinate(it, y)
                }
            } else {
                (other.x .. x).map { it ->
                    Coordinate(it, y)
                }
            }
        } else if (diagonals) {
            val xs = (x .. other.x) + (x downTo other.x)
            val ys = (y .. other.y) + (y downTo other.y)
            xs.zip(ys) { a, b ->
                Coordinate(a, b)
            }
        } else {
            listOf()
        }
    }
}

class Diagram(val size: Int) {
    val array = Array(size) { IntArray(size) }

    fun populate(coordinates: List<Coordinate>) {
        coordinates.forEach { coord ->
            val currentValue = array[coord.y][coord.x]
            array[coord.y][coord.x] = currentValue + 1
        }

        debug(toString())
    }

    fun overlappingPointCount(): Int {
        return array.map { row ->
            row.filter { it >= 2 }.size
        }.sum()
    }
    
    override fun toString(): String {
        return array.map { row ->
            row.map { col ->
                if (col == 0) {
                    "."
                } else {
                    col
                }
            }.joinToString(separator = "")
        }.joinToString(separator = "\n")
    }
}

fun main(vararg args: String) {
    setDebug(DEBUG)

    fun part1(input: List<String>): Int {
        var diagramSize = 0
        val allCoordinates = Coordinate.fromInputList(input) { coord ->
            if (coord.x > diagramSize) {
                diagramSize = coord.x + 1
            }
            if (coord.y > diagramSize) {
                diagramSize = coord.y + 1
            }
        }

        debug("Diagram size: $diagramSize")
        val diagram = Diagram(diagramSize)
        diagram.populate(allCoordinates)
        val overlapPoints = diagram.overlappingPointCount()
        debug(overlapPoints)
        return overlapPoints
    }

    fun part2(input: List<String>): Int {
        var diagramSize = 0
        val allCoordinates = Coordinate.fromInputList(input, diagonals = true) { coord ->
            if (coord.x > diagramSize) {
                diagramSize = coord.x + 1
            }
            if (coord.y > diagramSize) {
                diagramSize = coord.y + 1
            }
        }

        debug("Diagram size: $diagramSize")
        val diagram = Diagram(diagramSize)
        diagram.populate(allCoordinates)
        val overlapPoints = diagram.overlappingPointCount()
        debug(overlapPoints)
        return overlapPoints
    }

    val day = args[0]
    println(day)
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("$day/${day}_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("$day/${day}")
    println(part1(input))
    println(part2(input))
}
