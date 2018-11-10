package com.programmer74.signalprocessing.hartleytransformations

import org.junit.Test

import com.programmer74.signalprocessing.utils.TestUtils.assertArraysAreEqual
import com.programmer74.signalprocessing.utils.TestUtils.printArray

class HartleyTransformationsTest {

  @Test
  fun hartleyTransformWorks() {
    val source = doubleArrayOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    printArray("Source", source)

    val computed = HartleyTransformations.computeDiscreteHartleyTransform(source)
    printArray("Computed", computed)

    val computedBack = HartleyTransformations.computeReverseDiscreteHartleyTransform(computed)
    printArray("Computed Back", computedBack)

    assertArraysAreEqual(source, computedBack)
  }

  @Test
  fun hartley2DTransformWorks() {
    val source = arrayOf(doubleArrayOf(0.0, 1.0, 2.0), doubleArrayOf(3.0, 4.0, 5.0), doubleArrayOf(6.0, 7.0, 8.0))
    printArray("Source", source)

    val computed = HartleyTransformations.computeDiscreteHartleyTransform(source)
    printArray("Computed", computed)

    val computedBack = HartleyTransformations.computeReverseDiscreteHartleyTransform(computed)
    printArray("Computed Back", computedBack)

    assertArraysAreEqual(source, computedBack)
  }

  @Test
  fun hartleyMatrixGenerationWorks() {
    val matrix2 = arrayOf(doubleArrayOf(1.0, 1.0), doubleArrayOf(1.0, -1.0))
    val computedMatrix2 = HartleyTransformations.computeHartleyMatrix(2)
    printArray("HartleyMatrix2", computedMatrix2)
    assertArraysAreEqual(matrix2, computedMatrix2)

    val matrix4 = arrayOf(doubleArrayOf(1.0, 1.0, 1.0, 1.0), doubleArrayOf(1.0, 1.0, -1.0, -1.0), doubleArrayOf(1.0, -1.0, 1.0, -1.0), doubleArrayOf(1.0, -1.0, -1.0, 1.0))
    val computedMatrix4 = HartleyTransformations.computeHartleyMatrix(4)
    printArray("HartleyMatrix4", computedMatrix4)
    assertArraysAreEqual(matrix4, computedMatrix4)
  }
}
