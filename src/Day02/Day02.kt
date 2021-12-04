fun main(vararg args: String) {
    fun part1(input: List<String>): Int {
        var forward = 0
        var depth = 0
        input.forEach { it ->
            val (type, value) = it.split(" ")
            when (type) {
                "forward" -> forward += value.toInt()
                "up" -> depth -= value.toInt()
                "down" -> depth += value.toInt()
            }
        }

        return forward * depth
    }

    fun part2(input: List<String>): Int {
        var depth = 0
        var forward = 0
        var aim = 0
        input.forEach { it ->
            val (type, value) = it.split(" ")
            when (type) {
                "forward" -> {
                    forward += value.toInt()
                    depth += value.toInt() * aim
                }
                "up" -> aim -= value.toInt()
                "down" -> aim += value.toInt()
            }
        }
        return forward * depth
    }

    val day = args[0]
    println(day)
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("$day/${day}_test")
    check(part2(testInput) == 900)

    val input = readInput("$day/${day}")
    println(part1(input))
    println(part2(input))
}
