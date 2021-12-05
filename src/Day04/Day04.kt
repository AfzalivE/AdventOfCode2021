class Board {
    data class BoardItem(
        val value: Int,
        var matched: Boolean = false
    )
    val array = Array(5) { listOf<BoardItem>() }

    fun addRow(rowId: Int, row: String): Board {
        val rowNums = row.split(" ").filter { !it.isEmpty() }.map { it.toInt() }
        array[rowId] = rowNums.map { BoardItem(it) }

        return this
    }

    fun matchNumber(num: Int) {
        array.forEachIndexed { index, row ->
            val rowIndex = row.indexOfFirst {
                it.value == num
            }
            if (rowIndex != -1) {
                array[index][rowIndex].matched = true
                // println("Matched: $num at $index, $rowIndex")
            }
        }
    }

    fun checkWinner() : Boolean {
        array.forEach { row ->
            if (row.filter { it.matched == true }.size == 5) return true
        }

        for (i in 0 until 5) {
            var allTrue = true
            for (j in 0 until 5) {
                if (!allTrue || array[j][i].matched != true) {
                    allTrue = false    
                }
            }
            if (allTrue) {
                return true
            }
        }

        return false
    }

    fun score(num: Int) : Int {
        return num * array.map { row ->
            row.filter { it.matched == false }.sumOf { it.value }
        }.sum()
    }

    override fun toString(): String {
        return array.map { row ->
            row
        }.toString()
    }
}

fun runDraw(boards: List<Board>, drawn: List<Int>): Pair<Int, Int> {
    for (i in 0 until drawn.size) {
        val num = drawn[i]
        boards.forEachIndexed { index, board -> 
            board.matchNumber(num.toInt())
            if (board.checkWinner()) {
                println("Board $index has won!")
                return index to num
            }
        }
    }

    return -1 to -1
}

fun runDraw2(boards: List<Board>, drawn: List<Int>): Pair<Int, Int> {
    val winners = mutableSetOf<Int>()
    var currentNum = -1
    for (i in 0 until drawn.size) {
        if (winners.size < boards.size) {
            val num = drawn[i]
            boards.forEachIndexed { index, board -> 
                board.matchNumber(num.toInt())
                if (board.checkWinner()) {
                    // if (!winners.contains(index)) {
                        println("Board $index has won at $num!")
                        winners.add(index)
                        currentNum = num
                    // }
                }
            }
        }
    }

    return winners.last() to currentNum
}

fun parseBoards(input: List<String>) : List<Board> {
    val boards = mutableListOf<Board>()
    var currentRow = 0
    var currentBoard = Board()
    input.forEach { row ->
        // println("row: $row")
        if (row.isEmpty()) {
            // println("Adding current board: ${currentBoard.toString()}")
            boards.add(currentBoard)
            // println("new board")
            currentRow = 0
            currentBoard = Board()
        } else {
            currentBoard.addRow(currentRow, row)
            currentRow += 1
        }
    }

    // last board
    // println("Adding current board: ${currentBoard.toString()}")
    boards.add(currentBoard)

    return boards
}

fun main(vararg args: String) {
    fun part1(input: List<String>): Int {
        val drawn = input[0]
        val boards = parseBoards(input.drop(2))

        val (winner, lastDraw) = runDraw(boards, drawn.split(",").map { it.toInt()})
        val score = boards[winner].score(lastDraw)
        println(score)
        return score
    }

    fun part2(input: List<String>): Int {
        val drawn = input[0]
        val boards = parseBoards(input.drop(2))

        val (winner, lastDraw) = runDraw2(boards, drawn.split(",").map { it.toInt()})
        val score = boards[winner].score(lastDraw)
        println(score)
        return score
    }

    val day = args[0]
    println(day)
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("$day/${day}_test")
    check(part2(testInput) == 1924)

    val input = readInput("$day/${day}")
    println(part1(input))
    println(part2(input))
}
