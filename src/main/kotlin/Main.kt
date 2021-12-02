import java.io.File

fun day01() {
  fun increases(items: Pair<Int, Int>) = if (items.second > items.first) 1 else 0
  fun Sequence<Int>.q1() = zipWithNext().map(::increases).sum()
  fun Sequence<Int>.q2() = windowed(3).map(Iterable<Int>::sum).q1()
  fun run(q: Sequence<Int>.() -> Int) = File("day1input.txt").useLines {
    println("day01 $q: ${it.map(String::toInt).q()}")
  }
  run(Sequence<Int>::q1)
  run(Sequence<Int>::q2)
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

  fun run(q: Sequence<String>.() -> Int) = File("day2input.txt").useLines { println("day02 $q: ${it.q()}") }

  run(Sequence<String>::q1)
  run(Sequence<String>::q2)
}

fun main(args: Array<String>) {
  day01()
  day02()
}