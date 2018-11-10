package com.programmer74.signalprocessing.hartleytransformations

class HartleyTransformations {

  companion object {

    private fun cas(x: Double): Double {
      return Math.cos(x) + Math.sin(x)
    }

    fun computeDiscreteHartleyTransform(input: DoubleArray): DoubleArray {
      val N = input.size
      val result = DoubleArray(N)
      var sum = 0.0

      for (n in 0..N - 1) {
        sum = 0.0
        for (k in 0..N - 1) {
          sum += input[k] * computeCk(N, n * k)
        }
        result[n] = sum / N
      }
      return result
    }

    fun computeReverseDiscreteHartleyTransform(input: DoubleArray): DoubleArray {
      val N = input.size
      val result = DoubleArray(N)
      var sum = 0.0

      for (m in 0..N - 1) {
        sum = 0.0
        for (n in 0..N - 1) {
          sum += input[n] * computeCk(N, m * n)
        }
        result[m] = sum
      }
      return result
    }

    fun computeDiscreteHartleyTransform(a: Array<DoubleArray>): Array<DoubleArray> {

      val input = a.clone()

      val N = input.size
      val result = Array(N) { DoubleArray(N) }
      var sum = 0.0

      for (i in 0..N - 1) {
        //processing rows
        input[i] = computeDiscreteHartleyTransform(input[i])
        //      result[i] = computeDiscreteHartleyTransform(input[i]);
      }

      for (j in 0..N - 1) {
        //processing columns
        for (n in 0..N - 1) {
          sum = 0.0
          for (k in 0..N - 1) {
            sum += input[k][j] * computeCk(N, n * k)
          }
          result[n][j] = sum / N
        }
      }

      return result
    }

    fun computeReverseDiscreteHartleyTransform(a: Array<DoubleArray>): Array<DoubleArray> {

      val input = a.clone()

      val N = input.size
      val result = Array(N) { DoubleArray(N) }
      var sum = 0.0

      for (i in 0..N - 1) {
        //processing rows
        input[i] = computeReverseDiscreteHartleyTransform(input[i])
        //      result[i] = computeDiscreteHartleyTransform(input[i]);
      }

      for (j in 0..N - 1) {
        //processing columns
        for (n in 0..N - 1) {
          sum = 0.0
          for (k in 0..N - 1) {
            sum += input[k][j] * computeCk(N, n * k)
          }
          result[n][j] = sum
        }
      }

      return result
    }

    private fun computeCk(N: Int, k: Int): Double {
      return cas(2.0 * Math.PI * k.toDouble() / N.toDouble())
    }

    fun computeHartleyMatrix(N: Int): Array<DoubleArray> {
      val result = Array(N) { DoubleArray(N) }
      for (i in 0..N - 1) {
        for (j in 0..N - 1) {
          result[i][j] = computeCk(N, i * j)
        }
      }
      return result
    }
  }
}
