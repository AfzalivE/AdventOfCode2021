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
    val testInput = readInput("$day/${day}_test")
    check(part1(testInput) == 6)

    val input = readInput("$day/${day}")
    println(part1(input))
    println(part2(input))
}
