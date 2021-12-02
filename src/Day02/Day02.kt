fun main(vararg args: String) {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val day = args[0]
    println(day)
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("$day/Day02_test")
    check(part2(testInput) == 5)

    val input = readInput("$day/Day02")
    println(part1(input))
    println(part2(input))
}
