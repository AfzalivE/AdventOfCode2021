fun main(vararg args: String) {
    fun part1(input: List<String>): Int {
        var increases = 0
        input.reduce { last, current ->
            val curr = current.toInt()
            val prev = last.toInt()
            if (curr > prev) {
                increases += 1
            }
            current
        }
        return increases
    }

    fun part2(input: List<String>): Int {
        var increases = 0
        val intInput = input.map { it.toInt() }
        intInput.windowed(3).reduce { lastWindow, currentWindow ->
            val lastWindowSum = lastWindow.sum()
            val currentWindowSum = currentWindow.sum()
            if (currentWindowSum > lastWindowSum) {
                increases += 1
            }
            currentWindow
        }
        return increases
    }

    val day = args[0]
    println(day)
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("$day/${day}_test")
    check(part2(testInput) == 5)

    val input = readInput("$day/${day}")
    println(part1(input))
    println(part2(input))
}
