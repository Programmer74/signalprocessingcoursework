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

  constructor(x: Double, bitsSize: Int) : this (x, bitsSize - (bitsSize) * 3 / 4, (bitsSize) * 3 / 4 - 1)

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
    var exponent = exponentOffset
    if (xCopy > 1) {
      while (xCopy >= radix) {
        xCopy /= radix
        exponent++
      }
    } else {
      while (xCopy < 1) {
        xCopy *= radix
        exponent--
      }
    }
    log("exponent: ${exponent}")
    for (i in bitsPerE downTo 1) {
      bits.set(i, exponent % 2 != 0)
      exponent /= 2
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

  override fun toString(): String {
    val sb = StringBuilder()
    for (i in 0 until bitsSize) {
      sb.append("${if (bits.get(i)) 1 else 0}")
    }
    return sb.toString()
  }

  override fun getValue(): Double {

    log("=======--------")

    val sign: Int = if (bits.get(0)) -1 else 1
    log("sign: ${sign}")

    var exponent = 0
    for (i in 0 until bitsPerE) {
      if (bits.get(bitsPerE - i)) {
        exponent += Math.pow(2.0, i.toDouble()).toInt()
      }
    }
    log("exponent: ${exponent}")
    exponent -= exponentOffset

    var mantissa = 1.0
    var power = -1.0
    for (i in bitsPerE + 1 until bitsSize) {
      if (bits.get(i)) {
        mantissa += Math.pow(2.0, power)
      }
      power--
    }
    log("mantissa: ${mantissa}")

    val res = sign * Math.pow(2.0, exponent.toDouble()) * mantissa
    log("Value: ${res}")
    return res
  }

  override fun plus(y: Double): Numeric {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun plus(y: Numeric): Numeric {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun minus(y: Double): Numeric {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun minus(y: Numeric): Numeric {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun times(y: Double): Numeric {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun times(y: Numeric): Numeric {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun div(y: Double): Numeric {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun div(y: Numeric): Numeric {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun computeSin(): Numeric {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun computeCos(): Numeric {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun equals(other: Any?): Boolean {
    if (other !is Numeric) {
      return false
    }
    return Math.abs(other.getValue() - this.getValue()) < 0.0001
  }
}