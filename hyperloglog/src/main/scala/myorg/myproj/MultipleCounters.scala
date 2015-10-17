package myorg.myproj

import myorg.myproj.counters.ApproximateCounter

class MultipleCounters(counters: Array[ApproximateCounter]){
  def add(obj: Long): Unit = {
    for(counter <- counters){
      counter.add(obj)
    }
  }

  def distinctCount(cntrId: Int): Double = {
    counters(cntrId).distinctCount()
  }
}
