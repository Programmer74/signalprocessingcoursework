package com.programmer74.signalprocessing.hartleytransformations

import com.programmer74.signalprocessing.RoundStrategy
import com.programmer74.signalprocessing.customnumerics.CustomFixedPointNumeric
import com.programmer74.signalprocessing.customnumerics.DoubleNumeric
import com.programmer74.signalprocessing.customnumerics.Numeric

class HartleyTransformations {

  private val weightKBits: Int

  private val resultBits: Int

  private val roundingStrategy: RoundStrategy

  constructor() : this (0, 0)

  constructor(n2: Int, n3: Int) : this (n2, n3, RoundStrategy.ALWAYS_DOWN)

  constructor(n2: Int, n3: Int, roundStrategy: RoundStrategy) {
    this.weightKBits = n2
    this.resultBits = n3
    this.roundingStrategy = roundStrategy
  }

  private fun customFPTOf(x: Double) =
      CustomFixedPointNumeric(x, weightKBits - 3, 2, roundingStrategy)

  private fun customFPROf(x: Double) =
      CustomFixedPointNumeric(x, resultBits - 3, 2, roundingStrategy)

  private fun valueOf(x: Double): Numeric =
      if (weightKBits == 0 && resultBits == 0) {
        DoubleNumeric(x)
      } else {
        customFPTOf(x)
      }

  private fun resultOf(N: Int): Array<Numeric> =
      if (weightKBits == 0 && resultBits == 0) {
        Array(N, { DoubleNumeric(0.0) })
      } else {
        Array(N, { customFPROf(0.0) })
      }

  private fun result2DOf(N: Int): Array<Array<Numeric>> =
      if (weightKBits == 0 && resultBits == 0) {
        Array(N, { Array(N, { DoubleNumeric(0.0) }) })
            as Array<Array<Numeric>>
      } else {
        Array(N, { Array(N, { customFPROf(0.0) }) })
            as Array<Array<Numeric>>
      }

  private fun cas(x: Numeric) : Numeric {
    val a = x.computeCos()
    val b = x.computeSin()
    return a + b
  }

  fun computeDiscreteHartleyTransform(input: Array<Numeric>): Array<Numeric> {
    val N = input.size
    val result = resultOf(N)
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
    val result = resultOf(N)
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

    val N = a.size
    val input = ArrayList<Array<Numeric>>()
    val result = result2DOf(N)
    var sum: Numeric

    for (i in 0 until N) {
      //processing rows
      input.add(0, computeReverseDiscreteHartleyTransform(a[i]))
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
