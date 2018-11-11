package com.programmer74.signalprocessing.customnumerics

import org.assertj.core.api.AssertionsForClassTypes
import org.assertj.core.data.Percentage
import org.junit.Test

class CustomFixedPointNumericsTest {

  @Test
  fun `custom fixed point numerics works fine for custom bits size`() {
    val arguments = listOf(0.0, 1.0, -1.0, -0.1231, 0.312312, 0.5)
    val bitsAmount = listOf(10, 12, 16, 24, 32)

    arguments.forEach { double ->
      bitsAmount.forEach { bits ->
        println("At ${double} for bits ${bits}")
        val x = CustomFixedPointNumeric(double, bits)
        println(x.toBinaryString())
        AssertionsForClassTypes.assertThat(x.getValue()).isCloseTo(double, Percentage.withPercentage(5.0))
      }
    }
  }

  @Test
  fun `custom fixed point calculations work`() {
    val arguments = listOf(0.0, 1.0, -1.0, -0.1231, 0.312312, 0.5)
    val bitsAmount = listOf(10, 12, 16, 24, 32)

    arguments.forEach { double1 ->
      arguments.forEach { double2 ->
        bitsAmount.forEach { bits ->
          val x = CustomFixedPointNumeric(double1, bits)
          val y = CustomFixedPointNumeric(double2, bits)
          println("At ${double1} + ${double2} for bits ${bits}")
          val z = x + y
          AssertionsForClassTypes.assertThat(z.getValue())
              .isCloseTo(double1 + double2, Percentage.withPercentage(10.0))
        }
      }
    }
  }
}