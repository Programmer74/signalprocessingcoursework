package com.programmer74.signalprocessing.hartleytransformations

import com.programmer74.signalprocessing.customnumerics.customfixedPointNumericArrayOf
import com.programmer74.signalprocessing.utils.assertArraysAreEqual
import com.programmer74.signalprocessing.utils.printArray
import org.junit.Test

class CourseworkPlayground {

  @Test
  fun hartleyTransformWorksForCustomFixedPoint() {
    val source = customfixedPointNumericArrayOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    val transformsDouble = HartleyTransformations()
    val transformsFP = HartleyTransformations(12, 16)
    printArray("Source", source)

    val computedDouble = transformsDouble.computeDiscreteHartleyTransform(source)
    printArray("Computed via Double", computedDouble)

    val computedFP = transformsFP.computeDiscreteHartleyTransform(source)
    printArray("Computed via FixedP", computedFP)
  }
}