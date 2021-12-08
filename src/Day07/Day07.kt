import kotlin.math.*

private const val DEBUG = false

fun main(vararg args: String) {
    setDebug(DEBUG)

    fun calculateFuel1(target: Int, list: List<Int>): Int = list.sumOf {
        abs(it - target)
    }

    fun calculateFuel2(target: Int, list: List<Int>): Int {
        return list.sumOf {
            val n = abs(it - target)
            n * (n + 1) / 2
        }
    }

    fun part1(input: List<String>): Int {
        val positions = input.first().split(",").map { it.toInt() }
        val max = positions.maxOf { it }
        val target = positions.sorted()[positions.size / 2]
        debug(positions.sorted()[positions.size / 2])
        debug(calculateFuel1(target, positions))
        return calculateFuel1(target, positions)
    }

    fun part2(input: List<String>): Int {
        val positions = input.first().split(",").map { it.toInt() }
        val avg = positions.average()
        val roundedDownAvg = floor(avg).toInt()
        val roundedUpAvg = ceil(avg).toInt()
        val leastFuel = (roundedDownAvg..roundedUpAvg).minOf {
            calculateFuel2(it, positions)
        }
        return leastFuel
    }

    val day = args[0]
    println(day)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("$day/${day}_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("$day/${day}")
    println(part1(input))
    println(part2(input))
}
