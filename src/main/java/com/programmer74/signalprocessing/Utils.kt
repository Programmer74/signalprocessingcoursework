package com.programmer74.signalprocessing

import java.util.*


public fun BitSet.appendOne(actualSize: Int) {
  var remainder = true
  for (i in actualSize - 1 downTo 0) {

    var value = get(i).xor(remainder)
    var nextRemainder = get(i) && remainder

    set(i, value)
    remainder = nextRemainder

    if (!remainder) {
      break;
    }
  }
}

public enum class RoundStrategy {
  ALWAYS_UP, ALWAYS_DOWN, ROUND
}
