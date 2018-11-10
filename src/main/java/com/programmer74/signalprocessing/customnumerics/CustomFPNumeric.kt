package com.programmer74.signalprocessing.customnumerics

import java.util.*

class CustomFPNumeric(val x: Double, val bitsPerE: Int, val bitsPerM: Int) : Numeric {

  private val bitsPerS = 1
  private val bitsSize: Int = bitsPerE + bitsPerM + bitsPerS
  private val bits = BitSet(bitsSize)

  private val radix = 2
  private val exponentOffset =
      Math.pow(radix.toDouble(), (bitsPerE - 1).toDouble()).toInt() - 1

  private val shouldDebug = false

  constructor(x: Double, bitsSize: Int) : this (x, bitsSize - (bitsSize) * 2 / 3, (bitsSize) * 2 / 3 - 1)

  private fun log(s: String) {
    if (shouldDebug) {
      println(s)
    }
  }

  init {

    log("==============")
    log("Value: ${x}")
    log("bitsSize: ${bitsSize}")
    log("bitsPerM: ${bitsPerM}")
    log("bitsPerE: ${bitsPerE}")
    log("bitsPerS: ${bitsPerS}")

    val sign: Int = if (x < 0) 1 else 0
    log("sign: ${sign}")
    bits.set(0, sign)

    var xCopy = Math.abs(x)
    var exponent = if (xCopy != 0.0) exponentOffset else 0
    if (xCopy > 1) {
      while (xCopy >= radix && (xCopy != 0.0)) {
        xCopy /= radix
        exponent++
      }
    } else {
      while ((xCopy < 1) && (xCopy != 0.0)) {
        xCopy *= radix
        exponent--
      }
    }
    log("exponent: ${exponent}")
    for (i in bitsPerE downTo 1) {
      bits.set(i, exponent % 2 != 0)
      exponent /= 2
    }
    if (exponent > 0) {
      System.err.println("EXPONENT DOESNT FIT TO ${bitsPerE} FOR VALUE ${x} ")
    }

    var mantissa = xCopy
    log("mantissa: $mantissa")
    mantissa -= 1
    for (i in bitsPerE + 1 until bitsSize) {
      mantissa *= 2
      bits.set(i, mantissa >= 1)
      if (mantissa >= 1) {
        mantissa -= 1
        if (mantissa.equals(0)) {
          break
        }
      }
    }
  }

  override fun getValue(): Double {

    log("=======--------")

    val sign: Int = if (bits.get(0)) -1 else 1
    log("sign: ${sign}")

    var exponent = 0
    var exponentWasZero = false
    for (i in 0 until bitsPerE) {
      if (bits.get(bitsPerE - i)) {
        exponent += Math.pow(2.0, i.toDouble()).toInt()
      }
    }
    if (exponent != 0) {
      exponent -= exponentOffset
    } else {
      exponentWasZero = true
    }
    log("exponent: ${exponent}")

    var mantissa = 0.0
    var power = -1.0
    for (i in bitsPerE + 1 until bitsSize) {
      if (bits.get(i)) {
        mantissa += Math.pow(2.0, power)
      }
      power--
    }
    if (((mantissa == 0.0) && exponentWasZero).not()) {
      mantissa += 1
    }
    log("mantissa: ${mantissa}")

    val res = sign * Math.pow(2.0, exponent.toDouble()) * mantissa
    log("Value: ${res}")
    return res
  }

  override fun toString(): String {
    val sb = StringBuilder()
    for (i in 0 until bitsSize) {
      sb.append("${if (bits.get(i)) 1 else 0}")
    }
    return sb.toString()
  }

  override fun plus(y: Double): Numeric {
    return CustomFPNumeric(y + getValue(), bitsPerE, bitsPerM)
  }

  override fun plus(y: Numeric): Numeric {
    return CustomFPNumeric(y.getValue() + getValue(), bitsPerE, bitsPerM)
  }

  override fun minus(y: Double): Numeric {
    return CustomFPNumeric(y - getValue(), bitsPerE, bitsPerM)
  }

  override fun minus(y: Numeric): Numeric {
    return CustomFPNumeric(y.getValue() - getValue(), bitsPerE, bitsPerM)
  }

  override fun times(y: Double): Numeric {
    return CustomFPNumeric(y * getValue(), bitsPerE, bitsPerM)
  }

  override fun times(y: Numeric): Numeric {
    return CustomFPNumeric(y.getValue() * getValue(), bitsPerE, bitsPerM)
  }

  override fun div(y: Double): Numeric {
    return CustomFPNumeric(y / getValue(), bitsPerE, bitsPerM)
  }

  override fun div(y: Numeric): Numeric {
    return CustomFPNumeric(y.getValue() / getValue(), bitsPerE, bitsPerM)
  }

  override fun computeSin(): Numeric {
    return CustomFPNumeric(Math.sin(getValue()), bitsPerE, bitsPerM)
  }

  override fun computeCos(): Numeric {
    return CustomFPNumeric(Math.cos(getValue()), bitsPerE, bitsPerM)
  }

  override fun equals(other: Any?): Boolean {
    if (other !is Numeric) {
      return false
    }
    return Math.abs(other.getValue() - this.getValue()) < 0.0001
  }
}