package com.programmer74.signalprocessing.customnumerics

data class IntNumeric(private val value: Int) : Numeric {
  override operator fun plus(y: Double) : Numeric {
    return IntNumeric(value + y.toInt())
  }
  override operator fun plus(y: Numeric) : Numeric {
    return IntNumeric(value + y.getValue().toInt())
  }
  override operator fun minus(y: Double) : Numeric {
    return IntNumeric(value - y.toInt())
  }
  override operator fun minus(y: Numeric) : Numeric {
    return IntNumeric(value - y.getValue().toInt())
  }
  override operator fun times(y: Double) : Numeric {
    return IntNumeric(value * y.toInt())
  }
  override operator fun times(y: Numeric) : Numeric {
    return IntNumeric(value * y.getValue().toInt())
  }
  override operator fun div(y: Double) : Numeric {
    return IntNumeric(value / y.toInt())
  }
  override operator fun div(y: Numeric) : Numeric {
    return IntNumeric(value / y.getValue().toInt())
  }
  override fun equals(other: Any?): Boolean {
    if (other !is Numeric) {
      return false
    }
    return Math.abs(other.getValue() - value) < 0.0001
  }
  override fun getValue(): Double {
    return value.toDouble()
  }

  override fun computeSin(): Numeric {
    return IntNumeric(0)
  }

  override fun computeCos(): Numeric {
    return IntNumeric(0)
  }
}