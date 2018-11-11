package com.programmer74.signalprocessing.customnumerics

import com.programmer74.signalprocessing.RoundStrategy
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.assertj.core.data.Percentage
import org.junit.Test
import java.util.*


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
        assertThat(x.getValue()).isCloseTo(double, Percentage.withPercentage(5.0))
      }
    }
  }

  @Test
  fun `custom fixed point calculations work`() {
    val arguments = listOf(0.0, 1.0, -1.0, -0.1231, 0.312312, 0.5)
    val bitsAmount = listOf(12, 16, 24, 32)

    arguments.forEach { double1 ->
      arguments.forEach { double2 ->
        bitsAmount.forEach { bits ->
          val x = CustomFixedPointNumeric(double1, bits)
          val y = CustomFixedPointNumeric(double2, bits)
          println("At ${double1} + ${double2} for bits ${bits}")
          val z = x + y
          assertThat(z.getValue())
              .isCloseTo(double1 + double2, Percentage.withPercentage(10.0))
        }
      }
    }
  }

  fun BitSet.toBinaryString(): String {
    val sb = StringBuilder()
    for (i in 0 until size()) {
      sb.append("${if (get(i)) 1 else 0}")
    }
    return sb.toString()
  }

  @Test
  fun `rounding work`() {

    val sourceValues = listOf(5.0, 5.0 / 3.0, 5.0 / 9.0, 5.0 / 27.0)
    val expectedValuesForAlwaysUp = listOf(5.0, 1.671875, 0.5625, 0.1875)
    val expectedValuesForRounding = listOf(5.0, 1.6640625, 0.5546875, 0.1875)
    val expectedValuesForAlwaysDown = listOf(5.0, 1.6640625, 0.5546875, 0.1796875)
    val expectedValuesByDefault = listOf(5.0, 1.6640625, 0.5546875, 0.1796875)

    for (i in 0 until sourceValues.size) {
      val x = sourceValues[i]
      val cfU = CustomFixedPointNumeric(x, 24, 7, RoundStrategy.ALWAYS_UP)
      val cfR = CustomFixedPointNumeric(x, 24, 7, RoundStrategy.ROUND)
      val cfD = CustomFixedPointNumeric(x, 24, 7, RoundStrategy.ALWAYS_DOWN)

      assertThat(cfU.getValue()).isEqualTo(expectedValuesForAlwaysUp[i])
      assertThat(cfR.getValue()).isEqualTo(expectedValuesForRounding[i])
      assertThat(cfD.getValue()).isEqualTo(expectedValuesForAlwaysDown[i])

      val y = CustomFixedPointNumeric(x, 24, 7)
      assertThat(y.getValue()).isEqualTo(expectedValuesByDefault[i])
    }
  }
}