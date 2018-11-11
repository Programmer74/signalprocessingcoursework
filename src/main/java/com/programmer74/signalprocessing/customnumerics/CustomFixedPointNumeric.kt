package com.programmer74.signalprocessing.customnumerics

import com.programmer74.signalprocessing.RoundStrategy
import com.programmer74.signalprocessing.appendOne
import java.util.*

class CustomFixedPointNumeric : Numeric {

  private val bitsPerInteger: Int
  private val bitsPerFractional: Int
  private val bitsPerS = 1
  private val bitsSize: Int
  private val bits: BitSet
  private val roundStrategy: RoundStrategy

  private val shouldDebug = false

  private fun log(s: String) {
    if (shouldDebug) {
      println(s)
    }
  }

  private fun errlog(s: String) {
    if (shouldDebug) {
      System.err.println(s)
    }
  }

  constructor(x: Double, bitsSize: Int) : this (x, (bitsSize) * 1 / 4, bitsSize - (bitsSize) * 1 / 4 - 1,
      RoundStrategy.ALWAYS_DOWN)

  constructor(bitsPerInteger: Int, bitsPerFractional: Int, newbits: BitSet) :
      this (bitsPerInteger, bitsPerFractional, newbits, RoundStrategy.ALWAYS_DOWN)

  constructor(bitsPerInteger: Int, bitsPerFractional: Int, newbits: BitSet, roundStrategy: RoundStrategy) :
      this (0.0, bitsPerInteger, bitsPerFractional, roundStrategy){
    for (i in 0..bitsPerInteger + bitsPerFractional + 1) {
      this.bits.set(i, newbits.get(i))
    }
  }

  constructor(x: Double, bitsPerInteger: Int, bitsPerFractional: Int) :
      this (x, bitsPerInteger, bitsPerFractional, RoundStrategy.ALWAYS_DOWN)

  constructor(x: Double, bitsPerInteger: Int, bitsPerFractional: Int, roundStrategy: RoundStrategy) {
    this.bitsPerInteger = bitsPerInteger
    this.bitsPerFractional = bitsPerFractional
    this.bitsSize = bitsPerS + bitsPerInteger + bitsPerFractional
    this.bits = BitSet(bitsSize)
    this.roundStrategy = roundStrategy

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
    roundIfNecessary(frPart, sign)
  }

  private fun roundIfNecessary(fractionPart: Double, sign: Boolean) {
    var frPart = fractionPart
    if (frPart == 0.0) {
      errlog("No rounding required")
    } else {
      errlog("Rounding required")
      when (roundStrategy) {
        RoundStrategy.ALWAYS_DOWN -> {
          errlog("Dropping plane")
        }
        RoundStrategy.ALWAYS_UP -> {
          errlog("Increasing by one")
          bits.appendOne(bitsSize)
        }
        RoundStrategy.ROUND -> {
          errlog("Rounding...")
          var onesCount = 0
          for (i in 0..3) {
            frPart *= 2
            if (frPart >= 1.0) {
              onesCount++
              frPart -= 1
            }
          }
          if (onesCount >= 2) {
            errlog("Rounding said YES")
            bits.appendOne(bitsSize)
          } else {
            errlog("Rounding said NO")
          }
        }
      }
    }
    bits.set(0, sign)
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
    return CustomFixedPointNumeric(y + getValue(),
        bitsPerInteger, bitsPerFractional, roundStrategy)
  }

  override fun plus(y: Numeric): Numeric {
    return CustomFixedPointNumeric(y.getValue() + getValue(),
        bitsPerInteger, bitsPerFractional, roundStrategy)
  }

  override fun minus(y: Double): Numeric {
    return CustomFixedPointNumeric(y - getValue(),
        bitsPerInteger, bitsPerFractional, roundStrategy)
  }

  override fun minus(y: Numeric): Numeric {
    return CustomFixedPointNumeric(y.getValue() - getValue(),
        bitsPerInteger, bitsPerFractional, roundStrategy)
  }

  override fun times(y: Double): Numeric {
    return CustomFixedPointNumeric(y * getValue(),
        bitsPerInteger, bitsPerFractional, roundStrategy)
  }

  override fun times(y: Numeric): Numeric {
    return CustomFixedPointNumeric(y.getValue() * getValue(),
        bitsPerInteger, bitsPerFractional, roundStrategy)
  }

  override fun div(y: Double): Numeric {
    return CustomFixedPointNumeric(getValue() / y,
        bitsPerInteger, bitsPerFractional, roundStrategy)
  }

  override fun div(y: Numeric): Numeric {
    return CustomFixedPointNumeric(getValue() / y.getValue(),
        bitsPerInteger, bitsPerFractional, roundStrategy)
  }

  override fun computeSin(): Numeric {
    return CustomFixedPointNumeric(Math.sin(getValue()), bitsPerInteger, bitsPerFractional, roundStrategy)
  }

  override fun computeCos(): Numeric {
    return CustomFixedPointNumeric(Math.cos(getValue()), bitsPerInteger, bitsPerFractional, roundStrategy)
  }

  override fun equals(other: Any?): Boolean {
    if (other !is Numeric) {
      return false
    }
    return Math.abs(other.getValue() - this.getValue()) < 0.0001
  }
}