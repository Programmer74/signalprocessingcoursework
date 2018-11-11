package com.programmer74.signalprocessing.hartleytransformations

import com.programmer74.signalprocessing.RoundStrategy
import com.programmer74.signalprocessing.customnumerics.customIntegerNumericArrayOf
import com.programmer74.signalprocessing.utils.*
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
    println("RMSE: ${computeRMSE(computedDouble, computedFPD)}")

    val computedFPR = transformsFPR.computeDiscreteHartleyTransform(source)
    printArray("Computed via FixedPR", computedFPR)
    println("RMSE: ${computeRMSE(computedDouble, computedFPR)}")

    val computedFPU = transformsFPU.computeDiscreteHartleyTransform(source)
    printArray("Computed via FixedPU", computedFPU)
    println("RMSE: ${computeRMSE(computedDouble, computedFPU)}")
  }

  @Test
  fun getRMSEbyN() {
    var N: Int = 8

    val transformsDouble = HartleyTransformations()

    println("N,RMSE")

    while (N <= 512) {

      val source = createDummyMeasurement2D(n2, n3, N)

      val transformed = transformsDouble.computeDiscreteHartleyTransform(source)
      val transformedBack = transformsDouble.computeReverseDiscreteHartleyTransform(transformed)

      val RMSE = computeRMSE2D(source, transformedBack)
      println("${N},${RMSE}")

      N += 64
      if (N == 512 + 64) break
      if (N > 512) N = 512
    }
  }
}