package com.programmer74.signalprocessing.utils

import com.programmer74.signalprocessing.customnumerics.CustomFixedPointNumeric
import com.programmer74.signalprocessing.customnumerics.DoubleNumeric
import com.programmer74.signalprocessing.customnumerics.Numeric
import org.assertj.core.api.AssertionsForClassTypes.assertThat

fun printArray(name: String, x: Array<Numeric>) {
  println(name + "{")
  for (i in x.indices) {
    System.out.printf(" %f", x[i].getValue())
  }
  println("}")
}

fun printArray(name: String, x: Array<Array<Numeric>>) {
  println(name + "{")
  for (i in x.indices) {
    for (j in 0 until x[i].size) {
      System.out.printf(" %f", x[i][j].getValue())
    }
    println("")
  }
  println("}")
}

fun assertArraysAreEqual(a1: Array<Array<Numeric>>, a2: Array<Array<Numeric>>) {
  assertThat(a1.size).isEqualTo(a2.size)
  assertThat(a1[0].size).isEqualTo(a2[0].size)
  for (i in a1.indices) {
    for (j in 0 until a1[0].size) {
      assertThat(a1[i][j]).isEqualTo(a2[i][j])
    }
  }
}

fun assertArraysAreEqual(a1: Array<Numeric>, a2: Array<Numeric>) {
  assertThat(a1.size).isEqualTo(a2.size)
  for (i in a1.indices) {
    assertThat(a1[i]).isEqualTo(a2[i])
  }
}

fun computeRMSE(expected: Array<Numeric>, actual: Array<Numeric>): Double {
  val s = (0 until expected.size).sumByDouble { Math.pow((expected[it].getValue() - actual[it].getValue()), 2.0) }
  val sN = s / expected.size
  return Math.sqrt(sN)
}

fun computeRMSE2D(expected: Array<Array<Numeric>>, actual: Array<Array<Numeric>>): Double {
  var s = 0.0
  for (i in 0 until expected.size) {
    for (j in 0 until expected[i].size) {
      s += Math.pow((expected[i][j].getValue() - actual[i][j].getValue()), 2.0)
    }
  }
  val sN = s / expected.size
  return Math.sqrt(sN)
}

fun createDummyMeasurement(n1: Int, n2: Int, N: Int): Array<Numeric> {
  val bitsI = n1
  val bitsM = n2 - n1
  val result = Array<Numeric>(N, { CustomFixedPointNumeric(0.0, bitsI, bitsM) })
  var x: Double
  for (i in 0 until N) {
    x = (i % n1).toDouble()
    result[i] = CustomFixedPointNumeric(x, bitsI, bitsM)
  }
  return result
}

fun createDummyMeasurement2D1(n1: Int, n2: Int, N: Int): Array<Array<Numeric>> {
  val bitsI = n1
  val bitsM = n2 - n1
  val result =  Array(N, { Array(N, { CustomFixedPointNumeric(0.0, bitsI, bitsM) }) })
      as Array<Array<Numeric>>
  var x: Double
  for (i in 0 until N) {
    for (j in 0 until N) {
      x = ((i + j) % 64).toDouble()
      result[i][j] = CustomFixedPointNumeric(x, bitsI, bitsM)
    }
  }
  return result
}

fun createDummyMeasurement2D(n1: Int, n2: Int, N: Int): Array<Array<Numeric>> {
  val bitsI = n1
  val bitsM = n2 - n1
  val result =  Array(N, { Array(N, { DoubleNumeric(0.0) }) })
      as Array<Array<Numeric>>
  var x: Double
  for (i in 0 until N) {
    for (j in 0 until N) {
      x = ((i + j) % n1).toDouble()
      result[i][j] = DoubleNumeric(x)
    }
  }
  return result
}