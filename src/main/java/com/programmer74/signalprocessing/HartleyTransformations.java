package com.programmer74.signalprocessing;

public class HartleyTransformations {

  private static double cas(double x) {
    return Math.cos(x) + Math.sin(x);
  }

  public static double[] computeDiscreteHartleyTransform(double[] input) {
    int N = input.length;
    double[] result = new double[N];
    double sum = 0;

    for (int n = 0; n < N; n++) {
      sum = 0;
      for (int k = 0; k < N; k++) {
        sum += input[k] * computeCk(N, n * k);
      }
      result[n] = sum / N;
    }
    return result;
  }

  public static double[] computeReverseDiscreteHartleyTransform(double[] input) {
    int N = input.length;
    double[] result = new double[N];
    double sum = 0;

    for (int m = 0; m < N; m++) {
      sum = 0;
      for (int n = 0; n < N; n++) {
        sum += input[n] * computeCk(N, m * n);
      }
      result[m] = sum;
    }
    return result;
  }

  public static double[][] computeDiscreteHartleyTransform(double[][] a) {

    double[][] input = a.clone();

    int N = input.length;
    double[][] result = new double[N][N];
    double sum = 0;

    for (int i = 0; i < N; i++) {
      //processing rows
      input[i] = computeDiscreteHartleyTransform(input[i]);
//      result[i] = computeDiscreteHartleyTransform(input[i]);
    }

    for (int j = 0; j < N; j++) {
      //processing columns
      for (int n = 0; n < N; n++) {
        sum = 0;
        for (int k = 0; k < N; k++) {
          sum += input[k][j] * computeCk(N, n * k);
        }
        result[n][j] = sum / N;
      }
    }

    return result;
  }

  public static double[][] computeReverseDiscreteHartleyTransform(double[][] a) {

    double[][] input = a.clone();

    int N = input.length;
    double[][] result = new double[N][N];
    double sum = 0;

    for (int i = 0; i < N; i++) {
      //processing rows
      input[i] = computeReverseDiscreteHartleyTransform(input[i]);
//      result[i] = computeDiscreteHartleyTransform(input[i]);
    }

    for (int j = 0; j < N; j++) {
      //processing columns
      for (int n = 0; n < N; n++) {
        sum = 0;
        for (int k = 0; k < N; k++) {
          sum += input[k][j] * computeCk(N, n * k);
        }
        result[n][j] = sum;
      }
    }

    return result;
  }

  private static double computeCk(int N, int k) {
    return cas(2.0 * Math.PI * k / (double)N);
  }

  public static double[][] computeHartleyMatrix(int N) {
    double[][] result = new double[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        result[i][j] = computeCk(N, i * j);
      }
    }
    return result;
  }
}
