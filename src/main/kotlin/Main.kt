import java.io.File

fun run(path: String, solution: Sequence<String>.() -> Int) = File(path).useLines {
  println("$path $solution: ${it.solution()}")
}

fun day01() {
  fun increases(items: Pair<Int, Int>) = if (items.second > items.first) 1 else 0
  fun Sequence<Int>.countIncreases() = zipWithNext().map(::increases).sum()
  fun Sequence<String>.q1() = map(String::toInt).countIncreases()
  fun Sequence<String>.q2() = map(String::toInt).windowed(3).map(Iterable<Int>::sum).countIncreases()

  run("day1input.txt", Sequence<String>::q1)
  run("day1input.txt", Sequence<String>::q2)
}

fun day02() {
  fun String.magnitude() = split(' ')[1].toInt()
  fun String.toDelta() = when {
    startsWith("forward") -> Pair(magnitude(), 0)
    startsWith("up") -> Pair(0, -magnitude())
    startsWith("down") -> Pair(0, magnitude())
    else -> throw IllegalArgumentException("unexpected input: $this")
  }
  operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = Pair(first + other.first, second + other.second)
  fun Sequence<Pair<Int, Int>>.sum() = reduce { acc, pair -> acc + pair }
  fun Pair<Int, Int>.product() = first * second
  fun Sequence<String>.q1() = map(String::toDelta).sum().product()

  fun Triple<Int, Int, Int>.product() = first * second
  fun Triple<Int, Int, Int>.accumulateAimAndPosition(line: String) = when {
    line.startsWith("forward") -> Triple( first + line.magnitude(), second + third * line.magnitude(), third)
    line.startsWith("down") -> Triple(first, second, third + line.magnitude())
    line.startsWith("up") -> Triple(first, second, third - line.magnitude())
    else -> throw IllegalArgumentException("unexpected input: $line")
  }
  fun Sequence<String>.q2() = fold(Triple(0, 0, 0), Triple<Int, Int, Int>::accumulateAimAndPosition).product()

  run("day2input.txt", Sequence<String>::q1)
  run("day2input.txt", Sequence<String>::q2)
}

fun day03() {
  val width = 12
  operator fun List<Int>.plus(other: List<Int>) = zip(other).map { (i, j) -> i + j }
  fun Sequence<String>.gamma(): Int = map { it.map(Char::digitToInt) }
    .fold(Pair(0, List(width) { 0 })) { (count, frequenciesOfOnes), s -> Pair(count + 1, frequenciesOfOnes + s) }
    .let { (count, frequenciesOfOnes) ->
      frequenciesOfOnes.map { if (it > count / 2) 1 else 0 }.joinToString("").toInt(2)
    }
  fun Int.epsilon() = this xor List(width) { 1 }.joinToString("").toInt(2)
  fun Sequence<String>.q1() = gamma().let { it * it.epsilon() }

  run("day3input.txt", Sequence<String>::q1)
}

fun main(args: Array<String>) {
  day01()
  day02()
  day03()
}