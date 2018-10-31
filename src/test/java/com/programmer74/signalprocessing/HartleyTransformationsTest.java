package com.programmer74.signalprocessing;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HartleyTransformationsTest {

  private void printArray(String name, double[] x) {
    System.out.println(name + "{");
    for (int i = 0; i < x.length; i++) {
      System.out.printf(" %f", x[i]);
    }
    System.out.println("}");
  }

  private void print2DArray(String name, double[][] x) {
    System.out.println(name + "{");
    for (int i = 0; i < x.length; i++) {
      for (int j = 0; j < x[i].length; j++) {
        System.out.printf(" %f", x[i][j]);
      }
      System.out.println("");
    }
    System.out.println("}");
  }

  private void assertArraysAreEqual(double[][] a1, double[][] a2) {
    assertThat(a1.length).isEqualTo(a2.length);
    assertThat(a1[0].length).isEqualTo(a2[0].length);
    for (int i = 0; i < a1.length; i++) {
      for (int j = 0; j < a1[0].length; j++) {
        assertThat(Math.abs(a1[i][j] - a2[i][j])).isLessThan(0.0001);
      }
    }
  }

  private void assertArraysAreEqual(double[] a1, double[] a2) {
    assertThat(a1.length).isEqualTo(a2.length);
    for (int i = 0; i < a1.length; i++) {
      assertThat(Math.abs(a1[i] - a2[i])).isLessThan(0.0001);
    }
  }

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
  public void hartleyMatrixGenerationWorks() {
    double[][] matrix2 = new double[][] { {1.0, 1.0}, {1.0, -1.0} };
    double[][] computedMatrix2 = HartleyTransformations.computeHartleyMatrix(2);
    print2DArray("HartleyMatrix2", computedMatrix2);
    assertArraysAreEqual(matrix2, computedMatrix2);

    double[][] matrix4 = new double[][] {
        {1.0, 1.0, 1.0, 1.0},
        {1.0, 1.0, -1.0, -1.0},
        {1.0, -1.0, 1.0, -1.0} ,
        {1.0, -1.0, -1.0, 1.0} };
    double[][] computedMatrix4 = HartleyTransformations.computeHartleyMatrix(4);
    print2DArray("HartleyMatrix4", computedMatrix4);
    assertArraysAreEqual(matrix4, computedMatrix4);
  }
}
