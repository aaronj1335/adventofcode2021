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
    startsWith("forward") -> Pair(this.magnitude(), 0)
    startsWith("up") -> Pair(0, -this.magnitude())
    startsWith("down") -> Pair(0, this.magnitude())
    else -> throw IllegalArgumentException("unexpected input: $this")
  }
  operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = Pair(first + other.first, second + other.second)
  fun Sequence<Pair<Int, Int>>.sum() = reduce { acc, pair -> acc + pair }
  fun Pair<Int, Int>.product() = first * second
  fun Sequence<String>.q1() = map(String::toDelta).sum().product()

  fun Triple<Int, Int, Int>.product() = first * second
  fun Triple<Int, Int, Int>.accumulateAimAndPosition(line: String): Triple<Int, Int, Int> = when {
    line.startsWith("forward") -> Triple( first + line.magnitude(), second + third * line.magnitude(), third)
    line.startsWith("down") -> Triple(first, second, third + line.magnitude())
    line.startsWith("up") -> Triple(first, second, third - line.magnitude())
    else -> throw IllegalArgumentException("unexpected input: $line")
  }
  fun Sequence<String>.q2() = fold(Triple(0, 0, 0), Triple<Int, Int, Int>::accumulateAimAndPosition).product()

  run("day2input.txt", Sequence<String>::q1)
  run("day2input.txt", Sequence<String>::q2)
}

fun main(args: Array<String>) {
  day01()
  day02()
}