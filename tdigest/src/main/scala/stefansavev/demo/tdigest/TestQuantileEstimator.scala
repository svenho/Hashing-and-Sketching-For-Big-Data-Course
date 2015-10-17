package stefansavev.demo.tdigest

import java.util.Random

import stefansavev.demo.hyperloglog.ItemGenerator

object TestQuantileEstimator {
  def main (args: Array[String]): Unit = {
    val itemFrequencies = Array.range(1, 10000).map(_=> 10)
    val rnd = new Random(48481)
    val generator = new ItemGenerator(itemFrequencies, rnd)

    val tDigest = new TDunningTDigest() // new StreamLibTDigest()

    generator.generateItems(1, itemId => {
      tDigest.addValue(itemId.toDouble)
    })

    val threshold = tDigest.quantile(0.50)
    println(s"""${threshold}""")
  }
}

