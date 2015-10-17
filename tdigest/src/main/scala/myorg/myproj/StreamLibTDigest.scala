package myorg.myproj

import com.clearspring.analytics.stream.quantile.{TDigest => StreamLibTD}

class StreamLibTDigest extends QuantileEstimator{
  val tg = new StreamLibTD(1000.0)

  override def addValue(value: Double): Unit = {
    tg.add(value)
  }

  override def quantile(q: Double): Double = {
    tg.quantile(q)
  }
}
