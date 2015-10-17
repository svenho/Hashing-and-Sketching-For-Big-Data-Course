package myorg.myproj

import com.tdunning.math.stats.{TreeDigest => TDunningTD}

class TDunningTDigest extends QuantileEstimator{
  val tg = new TDunningTD(1000.0D)

  override def addValue(value: Double): Unit = {
    tg.add(value, 1)
  }

  override def quantile(q: Double): Double = {
    tg.quantile(q)
  }
}
