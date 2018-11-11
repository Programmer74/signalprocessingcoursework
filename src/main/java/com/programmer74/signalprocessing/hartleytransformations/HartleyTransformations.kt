package com.programmer74.signalprocessing.hartleytransformations

import com.programmer74.signalprocessing.customnumerics.CustomFloatingPointNumeric
import com.programmer74.signalprocessing.customnumerics.DoubleNumeric
import com.programmer74.signalprocessing.customnumerics.Numeric

class HartleyTransformations(val ref: Numeric) {

  private fun valueOf(x: Double): Numeric =
      when (ref) {
        is DoubleNumeric -> DoubleNumeric(x)
        is CustomFloatingPointNumeric -> CustomFloatingPointNumeric(x, ref.bitsPerE, ref.bitsPerM)
        else -> DoubleNumeric(x)
      }

  private fun cas(x: Numeric) : Numeric {
    val a = x.computeCos()
    val b = x.computeSin()
    return a + b
  }

  fun computeDiscreteHartleyTransform(input: Array<Numeric>): Array<Numeric> {
    val N = input.size
    val result = Array(N, { valueOf(0.0) })
    var sum: Numeric

    for (n in 0 until N) {
      sum = valueOf(0.0)
      for (k in 0 until N) {
        sum += input[k] * computeCk(N, n * k)
      }
      result[n] = sum / N.toDouble()
    }
    return result
  }

  fun computeReverseDiscreteHartleyTransform(input: Array<Numeric>): Array<Numeric> {
    val N = input.size
    val result = Array(N, { valueOf(0.0) })
    var sum: Numeric

    for (m in 0 until N) {
      sum = valueOf(0.0)
      for (n in 0 until N) {
        sum += input[n] * computeCk(N, m * n)
      }
      result[m] = sum
    }
    return result
  }

  fun computeDiscreteHartleyTransform(a: Array<Array<Numeric>>): Array<Array<Numeric>> {

    val input = a.clone()

    val N = input.size
    val result = Array(N, { Array(N, { valueOf(0.0) }) })
    var sum: Numeric

    for (i in 0 until N) {
      //processing rows
      input[i] = computeDiscreteHartleyTransform(input[i])
      //      result[i] = computeDiscreteHartleyTransform(input[i]);
    }

    for (j in 0 until N) {
      //processing columns
      for (n in 0 until N) {
        sum = valueOf(0.0)
        for (k in 0 until N) {
          sum += input[k][j] * computeCk(N, n * k)
        }
        result[n][j] = sum / N.toDouble()
      }
    }

    return result
  }

  fun computeReverseDiscreteHartleyTransform(a: Array<Array<Numeric>>): Array<Array<Numeric>> {

    val input = a.clone()

    val N = input.size
    val result = Array(N, { Array(N, { valueOf(0.0) }) })
    var sum: Numeric

    for (i in 0 until N) {
      //processing rows
      input[i] = computeReverseDiscreteHartleyTransform(input[i])
      //      result[i] = computeDiscreteHartleyTransform(input[i]);
    }

    for (j in 0 until N) {
      //processing columns
      for (n in 0 until N) {
        sum = valueOf(0.0)
        for (k in 0 until N) {
          sum += input[k][j] * computeCk(N, n * k)
        }
        result[n][j] = sum
      }
    }

    return result
  }

  private fun computeCk(N: Int, K: Int): Numeric {
    val pi = valueOf(Math.PI)
    val two = valueOf(2.0)
    val k = valueOf(K.toDouble())
    val n = valueOf(N.toDouble())
    return cas(two * pi * k / n)
  }

  fun computeHartleyMatrix(N: Int): Array<Array<Numeric>> {
    val result = Array(N, { Array(N, { valueOf(0.0)} ) })
    for (i in 0 until N) {
      for (j in 0 until N) {
        result[i][j] = computeCk(N, i * j)
      }
    }
    return result
  }
}
