package com.tw.workshop.tdd

import java.text.DecimalFormat

/**
 * Created by root on 14-12-13.
 */
object FilmClassifySystemExtensions {
  implicit class DoubleHelper(number: Double) {
    def withDecimalDigits(decimalNumber: Int = 2) = {
      new DecimalFormat("#." + "0"*decimalNumber).format(number).toDouble
    }
  }
}
