package com.programmer74.signalprocessing.utils

import org.assertj.core.api.AssertionsForClassTypes.assertThat

object TestUtils {

  fun printArray(name: String, x: DoubleArray) {
    println(name + "{")
    for (i in x.indices) {
      System.out.printf(" %f", x[i])
    }
    println("}")
  }

  fun printArray(name: String, x: Array<DoubleArray>) {
    println(name + "{")
    for (i in x.indices) {
      for (j in 0..x[i].size - 1) {
        System.out.printf(" %f", x[i][j])
      }
      println("")
    }
    println("}")
  }

  fun assertArraysAreEqual(a1: Array<DoubleArray>, a2: Array<DoubleArray>) {
    assertThat(a1.size).isEqualTo(a2.size)
    assertThat(a1[0].size).isEqualTo(a2[0].size)
    for (i in a1.indices) {
      for (j in 0..a1[0].size - 1) {
        assertThat(Math.abs(a1[i][j] - a2[i][j])).isLessThan(0.0001)
      }
    }
  }

  fun assertArraysAreEqual(a1: DoubleArray, a2: DoubleArray) {
    assertThat(a1.size).isEqualTo(a2.size)
    for (i in a1.indices) {
      assertThat(Math.abs(a1[i] - a2[i])).isLessThan(0.0001)
    }
  }
}
