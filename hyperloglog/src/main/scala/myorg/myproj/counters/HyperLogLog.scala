package myorg.myproj.counters

import myorg.myproj.hashing.Hasher
import java.lang.{Long => JLong}

class HyperLogLog(hasher: Hasher) extends ApproximateCounter{
  final val p = 10 //14 //number of bits used to represent the buckets
  final val m = 1 << p //number of counters
  val mask: Long = (1L << (64 - p)) - 1L //max value within the buckets, contains only 1's
  val counters = Array.ofDim[Int](m) //TODO: we can replace the ints by a byte or less

  override def add(obj: Long): Unit = {
    val hashCode = hasher.hash(obj)
    val bucketIndex = (hashCode >>> (64 - p)).toInt
    val bucketHashCode = hashCode & mask
    val sigma = (1 + JLong.numberOfTrailingZeros(bucketHashCode))
    counters(bucketIndex) = Math.max(counters(bucketIndex), sigma)
  }

  def distinctCount_(): Double = {
    var inverseSum = 0.0
    val offset = 0 //1

    for(maxZeroSuffix <- counters){
      val estimatedValuesInBucket = offset + (1 << maxZeroSuffix).toDouble
      inverseSum += 1.0/estimatedValuesInBucket
    }
    val harmonicMean = m.toDouble/inverseSum// - 2

    val corr = 0.7213/(1 + 1.079/m)
    corr*m*harmonicMean //multiply by m because we have m buckets

    //val avgHeight = counters.sum.toDouble/m
    //m*Math.exp(avgHeight*Math.log(2.0))
  }

  override def distinctCount(): Double = {
    var inverseSum = 0.0
    val offset = 1
    var numNonZeros = 0
    for(maxZeroSuffix <- counters){
      if (maxZeroSuffix >= 0) {
        val estimatedValuesInBucket = offset + (1 << maxZeroSuffix).toDouble
        inverseSum += 1.0 / estimatedValuesInBucket
        numNonZeros += 1
      }
    }
    val harmonicMean = numNonZeros.toDouble/inverseSum - 2

    val corr = 0.7213/(1 + 1.079/numNonZeros)
    corr*numNonZeros*harmonicMean //multiply by m because we have m buckets

    //val avgHeight = counters.sum.toDouble/m
    //m*Math.exp(avgHeight*Math.log(2.0))
  }
}
