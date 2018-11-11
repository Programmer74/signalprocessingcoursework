package com.programmer74.signalprocessing.hartleytransformations

import com.programmer74.signalprocessing.customnumerics.customIntegerNumericArrayOf
import com.programmer74.signalprocessing.utils.printArray
import org.junit.Test

class CourseworkPlayground {

  private val n1: Int = 10
  private val n2: Int = 12
  private val n3: Int = 16

  @Test
  fun hartleyTransformWorksForCustomFixedPoint() {
    val source = customIntegerNumericArrayOf(n1, n2, 0, 1, 2, 3, 4, 5, 6, 7, 8)
    val transformsDouble = HartleyTransformations()
    val transformsFP = HartleyTransformations(n2, n3)
    printArray("Source", source)

    val computedDouble = transformsDouble.computeDiscreteHartleyTransform(source)
    printArray("Computed via Double", computedDouble)

    val computedFP = transformsFP.computeDiscreteHartleyTransform(source)
    printArray("Computed via FixedP", computedFP)
  }
}