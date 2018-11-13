package com.programmer74.signalprocessing.hartleytransformations

import com.programmer74.signalprocessing.RoundStrategy
import com.programmer74.signalprocessing.customnumerics.customIntegerNumericArrayOf
import com.programmer74.signalprocessing.utils.*
import com.programmer74.signalprocessing.visuals.Array2DForm
import org.junit.Test
import java.util.concurrent.CountDownLatch

class CourseworkPlayground {

  private val n1: Int = 10
  private val n2: Int = 12
  private val n3: Int = 32

  @Test
  fun hartleyTransformWorksForCustomFixedPoint() {
    val source = customIntegerNumericArrayOf(n1, n2, 0, 1, 2, 3, 4, 5, 6, 7)
    val transformsDouble = HartleyTransformations()
    val transformsFPD = HartleyTransformations(n1, n2, n3, RoundStrategy.ALWAYS_DOWN)
    val transformsFPR = HartleyTransformations(n1, n2, n3, RoundStrategy.ROUND)
    val transformsFPU = HartleyTransformations(n1, n2, n3, RoundStrategy.ALWAYS_UP)
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

  @Test
  fun checkVisuals() {
    val N: Int = 48

    val transforms = HartleyTransformations(n1, n2, n3, RoundStrategy.ROUND)
    val source = createDummyMeasurement2D (n1, n2, N)

    val sourceForm = Array2DForm("Source", source, 256, 256)
    sourceForm.isVisible = true

    val computed = transforms.computeDiscreteHartleyTransform(source)
    val computedForm = Array2DForm("Computed", computed, 256, 256)
    computedForm.isVisible = true

    val computedBack = transforms.computeReverseDiscreteHartleyTransform(computed)
    val computedBackForm = Array2DForm("Computed Back", computedBack, 256, 256)
    computedBackForm.isVisible = true

    val RMSE = computeRMSE2D(source, computedBack)
    println("${N},${RMSE}")

    val latch = CountDownLatch(1)
    latch.await()
  }
}