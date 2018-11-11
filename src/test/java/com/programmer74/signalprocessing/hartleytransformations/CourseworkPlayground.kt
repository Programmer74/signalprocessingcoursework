package com.programmer74.signalprocessing.hartleytransformations

import com.programmer74.signalprocessing.RoundStrategy
import com.programmer74.signalprocessing.customnumerics.customIntegerNumericArrayOf
import com.programmer74.signalprocessing.utils.printArray
import org.junit.Test

class CourseworkPlayground {

  private val n1: Int = 10
  private val n2: Int = 12
  private val n3: Int = 16

  @Test
  fun hartleyTransformWorksForCustomFixedPoint() {
    val source = customIntegerNumericArrayOf(n1, n2, 0, 1, 2, 3, 4, 5, 6, 7)
    val transformsDouble = HartleyTransformations()
    val transformsFPD = HartleyTransformations(n2, n3, RoundStrategy.ALWAYS_DOWN)
    val transformsFPR = HartleyTransformations(n2, n3, RoundStrategy.ROUND)
    val transformsFPU = HartleyTransformations(n2, n3, RoundStrategy.ALWAYS_UP)
    printArray("Source", source)

    val computedDouble = transformsDouble.computeDiscreteHartleyTransform(source)
    printArray("Computed via Double", computedDouble)

    val computedFPD = transformsFPD.computeDiscreteHartleyTransform(source)
    printArray("Computed via FixedPD", computedFPD)

    val computedFPR = transformsFPR.computeDiscreteHartleyTransform(source)
    printArray("Computed via FixedPR", computedFPR)

    val computedFPU = transformsFPU.computeDiscreteHartleyTransform(source)
    printArray("Computed via FixedPU", computedFPU)
  }
}