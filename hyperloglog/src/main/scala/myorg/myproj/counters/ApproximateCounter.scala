package myorg.myproj.counters

trait ApproximateCounter {
  def add(obj: Long): Unit
  def distinctCount(): Double
}


