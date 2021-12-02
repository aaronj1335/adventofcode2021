import java.io.File

fun day01() {
  fun increases(items: Pair<Int, Int>) = if (items.second > items.first) 1 else 0
  fun Sequence<Int>.q1() = zipWithNext().map(::increases).sum()
  fun Sequence<Int>.q2() = windowed(3).map(Iterable<Int>::sum).q1()
  fun run(q: Sequence<Int>.() -> Int) = File("day1input.txt").useLines {
    println("$q: ${it.map(String::toInt).q()}")
  }
  run(Sequence<Int>::q1)
  run(Sequence<Int>::q2)
}

fun main(args: Array<String>) {
  day01()
}