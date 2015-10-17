package stefansavev.demo.tdigest

trait QuantileEstimator {
  def addValue(value: Double): Unit
  def quantile(q: Double): Double
}
