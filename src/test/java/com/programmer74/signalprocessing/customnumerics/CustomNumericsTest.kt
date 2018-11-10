package com.programmer74.signalprocessing.customnumerics

import org.junit.Test
import org.assertj.core.api.AssertionsForClassTypes.assertThat

class CustomNumericsTest () {

  @Test
  fun `double numerics seems to be working normally` () {
    val x = DoubleNumerics(5.0)
    val y = DoubleNumerics(4.0)
    val z = x + y
    assertThat(z.value).isEqualTo(9.0)
  }
}