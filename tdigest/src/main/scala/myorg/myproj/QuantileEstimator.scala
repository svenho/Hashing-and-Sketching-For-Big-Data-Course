package myorg.myproj

trait QuantileEstimator {
  def addValue(value: Double): Unit
  def quantile(q: Double): Double
}
