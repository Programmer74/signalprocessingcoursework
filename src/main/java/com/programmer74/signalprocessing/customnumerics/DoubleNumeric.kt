package com.programmer74.signalprocessing.customnumerics


data class DoubleNumeric(private val value: Double) : Numeric {
  operator fun plus(y: Double) : Numeric {
    return DoubleNumeric(value + y)
  }
  operator fun plus(y: Numeric) : Numeric {
    return DoubleNumeric(value + y.getValue())
  }
  operator fun minus(y: Double) : Numeric {
    return DoubleNumeric(value - y)
  }
  operator fun minus(y: Numeric) : Numeric {
    return DoubleNumeric(value - y.getValue())
  }
  operator fun times(y: Double) : Numeric {
    return DoubleNumeric(value * y)
  }
  operator fun times(y: Numeric) : Numeric {
    return DoubleNumeric(value * y.getValue())
  }
  operator fun div(y: Double) : Numeric {
    return DoubleNumeric(value / y)
  }
  operator fun div(y: Numeric) : Numeric {
    return DoubleNumeric(value / y.getValue())
  }
  override fun equals(other: Any?): Boolean {
    if (other !is Numeric) {
      return false
    }
    return Math.abs(other.getValue() - value) < 0.0001
  }
  override fun getValue(): Double {
    return value
  }

  override fun computeSin(): Numeric {
    return DoubleNumeric(Math.sin(value))
  }

  override fun computeCos(): Numeric {
    return DoubleNumeric(Math.cos(value))
  }
}