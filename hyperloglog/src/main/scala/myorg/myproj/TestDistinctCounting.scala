package myorg.myproj

import java.io.PrintWriter

import myorg.myproj.counters._
import myorg.myproj.hashing.{Murmur3Hasher, FakeHasher, ElasticSearchHasher}

import scala.util.Random

object TestDistinctCounting {
  def main (args: Array[String]): Unit = {
    val itemFrequencies = Array.range(1, 5000).map(_=> 1)
    val rnd = new Random(48481)
    val generator = new ItemGenerator(itemFrequencies, rnd)

    def createMultipleCounters(): MultipleCounters = {
      new MultipleCounters(Array(new NaiveCounter(), new EfficientLinearCounter(Murmur3Hasher)))// new EfficientLinearCounter(Murmur3Hasher)))
    }
    val writer = new PrintWriter("D:/tmp/approx-count.txt")
    val counters = createMultipleCounters()
    generator.generateItems(1, itemId => {
      counters.add(itemId)
      val (exact, approx) = (counters.distinctCount(0), counters.distinctCount(1))
      val diff = 100.0*(exact - approx)/exact
      println(s"""${exact} ${approx} ${diff}""")
      writer.write(s"""${exact}\t${approx}\n""")
    })
    writer.close()
  }
}
