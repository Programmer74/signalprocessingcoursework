package com.programmer74.signalprocessing.hartleytransformations

import com.programmer74.signalprocessing.RoundStrategy
import com.programmer74.signalprocessing.customnumerics.customIntegerNumericArrayOf
import com.programmer74.signalprocessing.utils.*
import com.programmer74.signalprocessing.visuals.Array2DForm
import org.junit.Ignore
import org.junit.Test
import java.util.concurrent.CountDownLatch

@Ignore
class CourseworkPlayground {

  private val n1: Int = 10
  private val n2: Int = 12
  private val n3: Int = 16

  @Test
  fun `0 Show 2d example with correct source image`() {
    val N: Int = 48

    val transforms = HartleyTransformations(n1, n2, n3, RoundStrategy.ROUND)
    val source = createMeasurement2D (n1, n2, N)

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

  @Test
  fun `1 Show how RMSE depends on N for ideal usecase`() {
    var N: Int = 8

    val transformsDouble = HartleyTransformations()

    println("N,RMSE")

    while (N <= 512) {

      val source = createMeasurement2D(n2, n3, N)

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
  fun `2 Show how RMSE depends on rounding for not-ideal usecase`() {

    var N: Int = 8

    val transformsDouble = HartleyTransformations()
    val transformsFPD = HartleyTransformations(n1, n2, n3, RoundStrategy.ALWAYS_DOWN)
    val transformsFPR = HartleyTransformations(n1, n2, n3, RoundStrategy.ROUND)
    val transformsFPU = HartleyTransformations(n1, n2, n3, RoundStrategy.ALWAYS_UP)

    println("N,RMSE trunc,RMSE round,RMSE up")

    while (N <= 512) {

      val source = createMeasurement2D(n2, n3, N)
      val computedDouble = transformsDouble.computeDiscreteHartleyTransform(source)

      print("$N")
      System.out.flush()

      val computedFPD = transformsFPD.computeDiscreteHartleyTransform(source)
      val rmseFPD = computeRMSE2D(computedDouble, computedFPD)
      print(",$rmseFPD")
      System.out.flush()

      val computedFPR = transformsFPR.computeDiscreteHartleyTransform(source)
      val rmseFPR = computeRMSE2D(computedDouble, computedFPR)
      print(",$rmseFPR")
      System.out.flush()

      val computedFPU = transformsFPU.computeDiscreteHartleyTransform(source)
      val rmseFPU = computeRMSE2D(computedDouble, computedFPU)
      println(",$rmseFPU")

      N += 64
      if (N == 512 + 64) break
      if (N > 512) N = 512
    }
  }

  @Test
  fun `3 Show how RMSE depends on changing source array bits size when rounding`() {

    var N: Int = 8

    val transformsDouble = HartleyTransformations()
    val transformsResultZero = HartleyTransformations(n1, n2, n3, RoundStrategy.ROUND)
    val transformsResultPlusTwoBits = HartleyTransformations(n1, n2, n3 + 2, RoundStrategy.ROUND)
    val transformsResultPlusFourBits = HartleyTransformations(n1, n2, n3 + 4, RoundStrategy.ROUND)
    val transformsWeightPlusTwoBits = HartleyTransformations(n1, n2 + 2, n3, RoundStrategy.ROUND)
    val transformsWeightPlusFourBits = HartleyTransformations(n1, n2 + 4, n3, RoundStrategy.ROUND)


    println("N,RMSE zero,RMSE result +2bits,RMSE result +4bits,RMSE weight +2bits,RMSE weight +4bits")

    while (N <= 64) {

      val source = createMeasurement2D(n2, n3, N)
      val computedDouble = transformsDouble.computeDiscreteHartleyTransform(source)

      print("$N")
      System.out.flush()

      val computedResultZero = transformsResultZero.computeDiscreteHartleyTransform(source)
      val rmseZero = computeRMSE2D(computedDouble, computedResultZero)
      print(",$rmseZero")
      System.out.flush()

      val computedResultPlus2 = transformsResultPlusTwoBits.computeDiscreteHartleyTransform(source)
      val rmseRPlus2 = computeRMSE2D(computedDouble, computedResultPlus2)
      print(",$rmseRPlus2")
      System.out.flush()

      val computedResultPlus4 = transformsResultPlusFourBits.computeDiscreteHartleyTransform(source)
      val rmseRPlus4 = computeRMSE2D(computedDouble, computedResultPlus4)
      print(",$rmseRPlus4")
      System.out.flush()

      val computedWeightPlus2 = transformsWeightPlusTwoBits.computeDiscreteHartleyTransform(source)
      val rmseWPlus2 = computeRMSE2D(computedDouble, computedWeightPlus2)
      print(",$rmseWPlus2")
      System.out.flush()

      val computedWeightPlus4 = transformsWeightPlusFourBits.computeDiscreteHartleyTransform(source)
      val rmseWPlus4 = computeRMSE2D(computedDouble, computedWeightPlus4)
      println(",$rmseWPlus4")
      System.out.flush()

      N += 8
    }
  }
}