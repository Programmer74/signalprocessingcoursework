package com.programmer74.signalprocessing.customnumerics

import org.junit.Test
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.assertj.core.data.Percentage

class CustomFPNumericsTest {
  @Test
  fun `custom fp numerics works fine`() {

    val arguments = HashMap<Double, String>()
    arguments[5.0] = "01000000101000000000000000000000"
    arguments[-5.0] = "11000000101000000000000000000000"
    arguments[-0.1231] = "10111101111111000001101111011010"
    arguments[0.312312] = "00111110100111111110011101011011"
    arguments[0.5] = "00111111000000000000000000000000"
    arguments[256.0] = "01000011100000000000000000000000"

    arguments.forEach { double, binary ->
      val x = CustomFPNumeric(double, 32)
      println(x)
      assertThat(x.toString()).isEqualTo(binary)
      assertThat(x.getValue()).isCloseTo(double, Percentage.withPercentage(1.0))
    }
  }
}