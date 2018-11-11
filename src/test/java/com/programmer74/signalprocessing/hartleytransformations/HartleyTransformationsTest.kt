package com.programmer74.signalprocessing.hartleytransformations

import com.programmer74.signalprocessing.customnumerics.*
import org.junit.Test

import com.programmer74.signalprocessing.utils.assertArraysAreEqual
import com.programmer74.signalprocessing.utils.printArray
import org.junit.Ignore

class HartleyTransformationsTest {

  private val transforms = HartleyTransformations()

  @Test
  fun hartleyTransformWorks() {
    val source = doubleNumericArrayOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
    printArray("Source", source)

    val computed = transforms.computeDiscreteHartleyTransform(source)
    printArray("Computed", computed)

    val computedBack = transforms.computeReverseDiscreteHartleyTransform(computed)
    printArray("Computed Back", computedBack)

    assertArraysAreEqual(source, computedBack)
  }

  @Ignore
  @Test
  fun hartley2DTransformWorks() {
    val source = arrayOf(
        doubleNumericArrayOf(0.0, 1.0, 2.0),
        doubleNumericArrayOf(3.0, 4.0, 5.0),
        doubleNumericArrayOf(6.0, 7.0, 8.0))
    printArray("Source", source)

    val computed = transforms.computeDiscreteHartleyTransform(source)
    printArray("Computed", computed)

    val computedBack = transforms.computeReverseDiscreteHartleyTransform(computed)
    printArray("Computed Back", computedBack)

    assertArraysAreEqual(source, computedBack)
  }

  @Test
  @Ignore
  fun hartleyMatrixGenerationWorks() {
    val matrix2 = arrayOf(doubleNumericArrayOf(1.0, 1.0), doubleNumericArrayOf(1.0, -1.0))
    val computedMatrix2 = transforms.computeHartleyMatrix(2)
    printArray("HartleyMatrix2", computedMatrix2)
    assertArraysAreEqual(matrix2, computedMatrix2)

    val matrix4 = arrayOf(
        doubleNumericArrayOf(1.0, 1.0, 1.0, 1.0),
        doubleNumericArrayOf(1.0, 1.0, -1.0, -1.0),
        doubleNumericArrayOf(1.0, -1.0, 1.0, -1.0),
        doubleNumericArrayOf(1.0, -1.0, -1.0, 1.0))
    val computedMatrix4 = transforms.computeHartleyMatrix(4)
    printArray("HartleyMatrix4", computedMatrix4)
    assertArraysAreEqual(matrix4, computedMatrix4)
  }
}
