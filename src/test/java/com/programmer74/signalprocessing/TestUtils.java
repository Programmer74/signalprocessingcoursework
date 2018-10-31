package com.programmer74.signalprocessing;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestUtils {

  public static void printArray(final String name, final double[] x) {
    System.out.println(name + "{");
    for (int i = 0; i < x.length; i++) {
      System.out.printf(" %f", x[i]);
    }
    System.out.println("}");
  }

  public static void printArray(final String name, final double[][] x) {
    System.out.println(name + "{");
    for (int i = 0; i < x.length; i++) {
      for (int j = 0; j < x[i].length; j++) {
        System.out.printf(" %f", x[i][j]);
      }
      System.out.println("");
    }
    System.out.println("}");
  }

  public static void assertArraysAreEqual(final double[][] a1, final double[][] a2) {
    assertThat(a1.length).isEqualTo(a2.length);
    assertThat(a1[0].length).isEqualTo(a2[0].length);
    for (int i = 0; i < a1.length; i++) {
      for (int j = 0; j < a1[0].length; j++) {
        assertThat(Math.abs(a1[i][j] - a2[i][j])).isLessThan(0.0001);
      }
    }
  }

  public static void assertArraysAreEqual(final double[] a1, final double[] a2) {
    assertThat(a1.length).isEqualTo(a2.length);
    for (int i = 0; i < a1.length; i++) {
      assertThat(Math.abs(a1[i] - a2[i])).isLessThan(0.0001);
    }
  }
}
