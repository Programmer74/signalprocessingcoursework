package com.programmer74.signalprocessing.customnumerics

import java.util.*

class CustomFixedPointNumeric(val x: Double, val bitsPerInteger: Int, val bitsPerFractional: Int) : Numeric {

  private val bitsPerS = 1
  private val bitsSize: Int = bitsPerS + bitsPerInteger + bitsPerFractional
  private val bits = BitSet(bitsSize)

  private val shouldDebug = false

  constructor(x: Double, bitsSize: Int) : this (x, (bitsSize) * 3 / 4, bitsSize - (bitsSize) * 3 / 4 - 1)

  private fun log(s: String) {
    if (shouldDebug) {
      println(s)
    }
  }

  init {

    log("==============")
    log("Value: ${x}")
    log("bitsSize: ${bitsSize}")
    log("bitsPerI: ${bitsPerInteger}")
    log("bitsPerF: ${bitsPerFractional}")

    val sign = (x < 0)
    bits.set(0, sign)
    val xCopy = Math.abs(x)

    var intPart = xCopy.toInt()
    var frPart = xCopy - intPart.toDouble()

    log("intPart: ${intPart}")
    log("frPart: ${frPart}")

    for (i in bitsPerInteger downTo 1) {
      bits.set(i, intPart % 2 != 0)
      intPart /= 2
    }
    if (intPart > 0) {
      System.err.println("INTPART DOESNT FIT TO ${bitsPerInteger} FOR VALUE ${x} ")
    }
    
    for (i in bitsPerInteger + 1 until bitsSize) {
      frPart *= 2
      bits.set(i, frPart >= 1)
      if (frPart >= 1) {
        frPart -= 1
        if (frPart.equals(0)) {
          break
        }
      }
    }
  }

  override fun getValue(): Double {

    log("=======--------")

    var intPart = 0
    for (i in 0 until bitsPerInteger) {
      if (bits.get(bitsPerInteger - i)) {
        intPart += Math.pow(2.0, i.toDouble()).toInt()
      }
    }
    log("intPart: ${intPart}")

    var frPart = 0.0
    var power = -1.0
    for (i in bitsPerInteger + 1 until bitsSize) {
      if (bits.get(i)) {
        frPart += Math.pow(2.0, power)
      }
      power--
    }

    log("frPart: ${frPart}")

    var res = intPart.toDouble() + frPart
    if (bits.get(0)) {
      res *= -1
    }
    log("Value: ${res}")
    return res
  }

  override fun toString(): String {
    return "CFdP: ${getValue()}"
  }

  fun toBinaryString(): String {
    val sb = StringBuilder()
    for (i in 0 until bitsSize) {
      sb.append("${if (bits.get(i)) 1 else 0}")
    }
    return sb.toString()
  }

  override fun plus(y: Double): Numeric {
    return CustomFixedPointNumeric(y + getValue(), bitsPerInteger, bitsPerFractional)
  }

  override fun plus(y: Numeric): Numeric {
    return CustomFixedPointNumeric(y.getValue() + getValue(), bitsPerInteger, bitsPerFractional)
  }

  override fun minus(y: Double): Numeric {
    return CustomFixedPointNumeric(y - getValue(), bitsPerInteger, bitsPerFractional)
  }

  override fun minus(y: Numeric): Numeric {
    return CustomFixedPointNumeric(y.getValue() - getValue(), bitsPerInteger, bitsPerFractional)
  }

  override fun times(y: Double): Numeric {
    return CustomFixedPointNumeric(y * getValue(), bitsPerInteger, bitsPerFractional)
  }

  override fun times(y: Numeric): Numeric {
    return CustomFixedPointNumeric(y.getValue() * getValue(), bitsPerInteger, bitsPerFractional)
  }

  override fun div(y: Double): Numeric {
    return CustomFixedPointNumeric(getValue() / y, bitsPerInteger, bitsPerFractional)
  }

  override fun div(y: Numeric): Numeric {
    return CustomFixedPointNumeric(getValue() / y.getValue(), bitsPerInteger, bitsPerFractional)
  }

  override fun computeSin(): Numeric {
    return CustomFixedPointNumeric(Math.sin(getValue()), bitsPerInteger, bitsPerFractional)
  }

  override fun computeCos(): Numeric {
    return CustomFixedPointNumeric(Math.cos(getValue()), bitsPerInteger, bitsPerFractional)
  }

  override fun equals(other: Any?): Boolean {
    if (other !is Numeric) {
      return false
    }
    return Math.abs(other.getValue() - this.getValue()) < 0.0001
  }
}