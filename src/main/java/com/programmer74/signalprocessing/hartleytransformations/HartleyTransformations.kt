package com.programmer74.signalprocessing.hartleytransformations

import com.programmer74.signalprocessing.RoundStrategy
import com.programmer74.signalprocessing.customnumerics.CustomFixedPointNumeric
import com.programmer74.signalprocessing.customnumerics.DoubleNumeric
import com.programmer74.signalprocessing.customnumerics.Numeric

class HartleyTransformations {

  private val sourceBits: Int

  private val weightKBits: Int

  private val resultBits: Int

  private val roundingStrategy: RoundStrategy

  constructor() : this (0, 0, 0)

  constructor(n1: Int, n2: Int, n3: Int) : this (n1, n2, n3, RoundStrategy.ALWAYS_DOWN)

  constructor(n1: Int, n2: Int, n3: Int, roundStrategy: RoundStrategy) {
    this.sourceBits = n1
    this.weightKBits = n2
    this.resultBits = n3
    this.roundingStrategy = roundStrategy
  }

  private fun actualValueOf(x: Double): Numeric = DoubleNumeric(x)

  private fun weightValueOf(x: Double): Numeric =
      if (weightKBits == 0 && resultBits == 0) {
        DoubleNumeric(x)
      } else {
        CustomFixedPointNumeric(x, 2, weightKBits - 2, roundingStrategy)
      }

  private fun resultValueOf(x: Double): Numeric =
      if (weightKBits == 0 && resultBits == 0) {
        DoubleNumeric(x)
      } else {
        if (resultBits == 16) {
          CustomFixedPointNumeric(x, 14, 2, roundingStrategy)
        } else {
          CustomFixedPointNumeric(x, 14, resultBits - 14, roundingStrategy)
        }
      }

  private fun zero() : Numeric = resultValueOf(0.0)

  private fun resultOf(N: Int): Array<Numeric> =
        Array(N, { resultValueOf(0.0) })

  private fun result2DOf(N: Int): Array<Array<Numeric>> =
        Array(N, { Array(N, { resultValueOf(0.0) }) })

  private fun cas(x: Numeric) : Numeric {
    val a = x.computeCos()
    val b = x.computeSin()
    val s = a + b
    return weightValueOf(s.getValue())
  }

  fun computeDiscreteHartleyTransform(input: Array<Numeric>): Array<Numeric> {
    val N = input.size
    val result = resultOf(N)
    var sum: Numeric

    for (n in 0 until N) {
      sum = zero()
      for (k in 0 until N) {
        sum += input[k] * computeCk(N, n * k)
      }
      result[n] = sum / N.toDouble()
    }
    return result
  }

  fun computeReverseDiscreteHartleyTransform(input: Array<Numeric>): Array<Numeric> {
    val N = input.size
    val result = resultOf(N)
    var sum: Numeric

    for (m in 0 until N) {
      sum = zero()
      for (n in 0 until N) {
        sum += input[n] * computeCk(N, m * n)
      }
      result[m] = sum
    }
    return result
  }

  fun computeDiscreteHartleyTransform(a: Array<Array<Numeric>>): Array<Array<Numeric>> {

    val N = a.size
    val input = ArrayList<Array<Numeric>>()
    val result = result2DOf(N)
    var sum: Numeric

    for (i in 0 until N) {
      //processing rows
      input.add(i, computeDiscreteHartleyTransform(a[i]))
      //      result[i] = computeDiscreteHartleyTransform(input[i]);
    }

    for (j in 0 until N) {
      //processing columns
      for (n in 0 until N) {
        sum = zero()
        for (k in 0 until N) {
          sum += input[k][j] * computeCk(N, n * k)
        }
        result[n][j] = sum / N.toDouble()
      }
    }

    return result
  }

  fun computeReverseDiscreteHartleyTransform(a: Array<Array<Numeric>>): Array<Array<Numeric>> {

    val N = a.size
    val input = ArrayList<Array<Numeric>>()
    val result = result2DOf(N)
    var sum: Numeric

    for (i in 0 until N) {
      //processing rows
      input.add(i, computeReverseDiscreteHartleyTransform(a[i]))
      //      result[i] = computeDiscreteHartleyTransform(input[i]);
    }

    for (j in 0 until N) {
      //processing columns
      for (n in 0 until N) {
        sum = zero()
        for (k in 0 until N) {
          sum += input[k][j] * computeCk(N, n * k)
        }
        result[n][j] = sum
      }
    }

    return result
  }

  private fun computeCk(N: Int, K: Int): Numeric {
    val pi = actualValueOf(Math.PI)
    val two = actualValueOf(2.0)
    val k = K.toDouble()
    val n = N.toDouble()
    val z = cas(two * pi * k / n)
    return z
  }

  fun computeHartleyMatrix(N: Int): Array<Array<Numeric>> {
    val result = Array(N, { Array(N, { zero() } ) })
    for (i in 0 until N) {
      for (j in 0 until N) {
        result[i][j] = computeCk(N, i * j)
      }
    }
    return result
  }
}
