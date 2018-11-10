package com.programmer74.signalprocessing.customnumerics

import org.junit.Test
import org.assertj.core.api.AssertionsForClassTypes.assertThat

class CustomNumericsTest () {

  @Test
  fun `double numerics addition works` () {
    val x = DoubleNumeric(5.0)
    val y = DoubleNumeric(4.0)
    val z = x + y
    assertThat(z).isEqualTo(DoubleNumeric(9.0))
  }

  @Test
  fun `double numerics trigonometry works` () {
    val x = 5.0
    val y = DoubleNumeric(x)
    val a = Math.sin(x)
    val b = y.computeSin()
    assertThat(a).isEqualTo(b.getValue())
  }
}