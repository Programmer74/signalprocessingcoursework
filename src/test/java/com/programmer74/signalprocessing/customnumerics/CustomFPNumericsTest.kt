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
      val x = CustomFPNumeric(double, 8, 23)
      println(x)
      assertThat(x.toString()).isEqualTo(binary)
      assertThat(x.getValue()).isCloseTo(double, Percentage.withPercentage(1.0))
    }
  }

  @Test
  fun `custom fp numerics works fine for custom bits size`() {
    val arguments = listOf(0.0, 1.0, 5.0, -5.0, -0.1231, 0.312312, 0.5, 256.0)
    val bitsAmount = listOf(10, 12, 16, 24, 32)

    arguments.forEach { double ->
      bitsAmount.forEach { bits ->
        println("At ${double} for bits ${bits}")
        val x = CustomFPNumeric(double, bits)
        println(x)
        assertThat(x.getValue()).isCloseTo(double, Percentage.withPercentage(3.0))
      }
    }
  }

  @Test
  fun `custom fp calculations work`() {
    val arguments = listOf(5.0, -5.0, -0.1231, 0.312312, 0.5)
    val bitsAmount = listOf(10, 12, 16, 24, 32)

    arguments.forEach { double1 ->
      arguments.forEach { double2 ->
        bitsAmount.forEach { bits ->
          val x = CustomFPNumeric(double1, bits)
          val y = CustomFPNumeric(double2, bits)
          println("At ${double1} + ${double2} for bits ${bits}")
          val z = x + y
          assertThat(z.getValue())
              .isCloseTo(double1 + double2, Percentage.withPercentage(7.0))
        }
      }
    }
  }
}