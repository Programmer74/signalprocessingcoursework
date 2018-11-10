package com.programmer74.signalprocessing.customnumerics


data class DoubleNumerics(override val value: Double) : Numerics {
  operator fun plus(y: Double) : Numerics {
    return DoubleNumerics(value + y)
  }
  operator fun plus(y: DoubleNumerics) : Numerics {
    return DoubleNumerics(value + y.value)
  }
  operator fun minus(y: Double) : Numerics {
    return DoubleNumerics(value - y)
  }
  operator fun minus(y: DoubleNumerics) : Numerics {
    return DoubleNumerics(value - y.value)
  }
  operator fun times(y: Double) : Numerics {
    return DoubleNumerics(value * y)
  }
  operator fun times(y: DoubleNumerics) : Numerics {
    return DoubleNumerics(value * y.value)
  }
  operator fun div(y: Double) : Numerics {
    return DoubleNumerics(value / y)
  }
  operator fun div(y: DoubleNumerics) : Numerics {
    return DoubleNumerics(value / y.value)
  }

  override fun equals(other: Any?): Boolean {
    if (other !is Numerics) {
      return false
    }
    return Math.abs(other.value - value) < 0.0001
  }
}