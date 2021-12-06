private const val DEBUG = false

fun main(vararg args: String) {
    setDebug(DEBUG)

    fun simulateDay(fishes: List<Int>): List<Int> {
        val newFishes = mutableListOf<Int>()
        fishes.filter { it == 0 }.forEach {
            newFishes.add(8)
        }

        val nextFishes = fishes.map { fish ->
            if (fish > 0) {
                fish - 1
            } else {
                6
            }
        }

        return nextFishes + newFishes
    }

    fun part1(input: List<String>): Int {
        val initial = input.first().split(",").map { it.toInt() }
        var currentState = initial
        repeat(80) {
            currentState = simulateDay(currentState)
        }
        debug(currentState)
        return currentState.size
    }

    fun simulateDay(fishMap: MutableMap<Int, Long>): MutableMap<Int, Long> {
        val newMap = mutableMapOf<Int, Long>()

        fishMap.mapKeys {
            val (key, value) = it
            if (key > 0) {
                newMap[key - 1] = value
            }
        }

        val newFishes = fishMap[0] ?: 0
        newMap[8] = (newMap[8] ?: 0) + newFishes
        newMap[6] = (newMap[6] ?: 0) + newFishes

        return newMap
    }

    fun part2(input: List<String>): Long {
        val initial = input.first().split(",").map { it.toInt() }
        var fishes = mutableMapOf<Int, Long>()
        initial.forEach {
            val existingFishes = fishes[it] ?: 0
            fishes[it] = existingFishes + 1
        }
        repeat(256) {
            fishes = simulateDay(fishes)
        }
        return fishes.values.sum()
    }

    val day = args[0]
    println(day)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("$day/${day}_test")
    check(part1(testInput) == 5934)
    check(part2(testInput) == 26984457539)

    val input = readInput("$day/${day}")
    println(part1(input))
    println(part2(input))
}
