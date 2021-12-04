

import kotlin.collections.mutableMapOf
import kotlin.math.pow

fun main(vararg args: String) {
    fun part1(input: List<String>): Int {
        val gamma = mutableListOf<Int>()
        val countsOfOnePerCol = mutableMapOf<Int, Int>()
        input.forEachIndexed { rowI, row ->
            row.forEachIndexed { col, char ->
                if (char == '1') {
                    // println("Found 1 on $rowI, $col")
                    countsOfOnePerCol[col] = (countsOfOnePerCol[col] ?: 0) + 1
                    // println("Incremented count at $col to ${countsOfOnePerCol[col]}")
                } else {
                    // println("Found 0 on $rowI, $col")
                }
            }
        }
        // println(countsOfOnePerCol)
        countsOfOnePerCol.toSortedMap().values.forEach { count ->
            if (input.size / 2 < count) {
                gamma.add(1)
            } else {
                gamma.add(0)
            }
        }

        val length = input[0].length
        val bits = 2f.pow(length)
        val gammaInt = gamma.joinToString(separator = "").toInt(radix = 2)
        val epsilonInt = bits + gammaInt.inv()
        return (gammaInt * epsilonInt).toInt()
    }

    fun part2(input: List<String>): Int {
        fun findRating(bitCriteria: Char): String {
            val otherBit = if (bitCriteria == '0') '1' else '0'
            var newInput = input
            for (i in 0 until input[0].length) {
                var countOfBit = 0
                if (newInput.size == 1) break
                newInput = newInput.map { row ->
                    if (row[i] == '1') {
                        countOfBit += 1
                    }
                    row
                }.filter { row ->
                    // println("CountOfBit for $i = $countOfBit, newInput size = ${newInput.size}")
                    // avoid case where odd input size causes this to match (3 >= 7 / 2)
                    if (countOfBit * 2 >= newInput.size) {
                        row[i] == bitCriteria
                    } else {
                        row[i] == otherBit
                    }
                }
            }
            // println(newInput)
            return newInput.first()
        }

        val o2Rating = findRating('1').toInt(radix = 2)
        val co2Rating = findRating('0').toInt(radix = 2)
        return o2Rating * co2Rating
    }

    val day = args[0]
    println(day)
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("$day/${day}_test")
    check(part2(testInput) == 230)

    val input = readInput("$day/${day}")
    println(part1(input))
    println(part2(input))
}
