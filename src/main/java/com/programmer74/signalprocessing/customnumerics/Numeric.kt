package com.programmer74.signalprocessing.customnumerics

interface Numeric {
  fun getValue(): Double
  fun computeSin(): Numeric
  fun computeCos(): Numeric
}