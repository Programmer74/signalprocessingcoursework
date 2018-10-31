package com.programmer74.signalprocessing;

import org.junit.Test;

import static com.programmer74.signalprocessing.TestUtils.assertArraysAreEqual;
import static com.programmer74.signalprocessing.TestUtils.printArray;

public class HartleyTransformationsTest {

  @Test
  public void hartleyTransformWorks() {
    double[] source = {0, 1, 2, 3, 4, 5, 6, 7};
    printArray("Source", source);

    double[] computed = HartleyTransformations.computeDiscreteHartleyTransform(source);
    printArray("Computed", computed);

    double[] computedBack = HartleyTransformations.computeReverseDiscreteHartleyTransform(computed);
    printArray("Computed Back", computedBack);

    assertArraysAreEqual(source, computedBack);
  }

  @Test
  public void hartley2DTransformWorks() {
    double[][] source = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
    printArray("Source", source);

    double[][] computed = HartleyTransformations.computeDiscreteHartleyTransform(source);
    printArray("Computed", computed);

    double[][] computedBack = HartleyTransformations.computeReverseDiscreteHartleyTransform(computed);
    printArray("Computed Back", computedBack);

    assertArraysAreEqual(source, computedBack);
  }

  @Test
  public void hartleyMatrixGenerationWorks() {
    double[][] matrix2 = new double[][] { {1.0, 1.0}, {1.0, -1.0} };
    double[][] computedMatrix2 = HartleyTransformations.computeHartleyMatrix(2);
    printArray("HartleyMatrix2", computedMatrix2);
    assertArraysAreEqual(matrix2, computedMatrix2);

    double[][] matrix4 = new double[][] {
        {1.0, 1.0, 1.0, 1.0},
        {1.0, 1.0, -1.0, -1.0},
        {1.0, -1.0, 1.0, -1.0} ,
        {1.0, -1.0, -1.0, 1.0} };
    double[][] computedMatrix4 = HartleyTransformations.computeHartleyMatrix(4);
    printArray("HartleyMatrix4", computedMatrix4);
    assertArraysAreEqual(matrix4, computedMatrix4);
  }
}
