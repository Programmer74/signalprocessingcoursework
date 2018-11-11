package com.programmer74.signalprocessing.customnumerics

interface Numeric {
  fun getValue(): Double

  operator fun plus(y: Double) : Numeric
  operator fun plus(y: Numeric) : Numeric

  operator fun minus(y: Double) : Numeric
  operator fun minus(y: Numeric) : Numeric

  operator fun times(y: Double) : Numeric
  operator fun times(y: Numeric) : Numeric

  operator fun div(y: Double) : Numeric
  operator fun div(y: Numeric) : Numeric

  fun computeSin(): Numeric
  fun computeCos(): Numeric
}

fun doubleNumericArrayOf(vararg elements: Double): Array<Numeric> {
  val result = Array<Numeric>(elements.size, { DoubleNumeric(0.0) })
  for (i in 0 until elements.size) {
    result[i] = DoubleNumeric(elements[i])
  }
  return result
}

fun customfloatPointNumericArrayOf(vararg elements: Double): Array<Numeric> {
  val bits = 32
  val result = Array<Numeric>(elements.size, { CustomFloatingPointNumeric(0.0, bits) })
  for (i in 0 until elements.size) {
    result[i] = CustomFloatingPointNumeric(elements[i], bits)
  }
  return result
}

fun customfixedPointNumericArrayOf(vararg elements: Double): Array<Numeric> {
  val bitsI = 11
  val bitsM = 20
  val result = Array<Numeric>(elements.size, { CustomFixedPointNumeric(0.0, bitsI, bitsM) })
  for (i in 0 until elements.size) {
    result[i] = CustomFixedPointNumeric(elements[i], bitsI, bitsM)
  }
  return result
}