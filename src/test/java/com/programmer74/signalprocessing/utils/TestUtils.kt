package com.programmer74.signalprocessing.utils

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

