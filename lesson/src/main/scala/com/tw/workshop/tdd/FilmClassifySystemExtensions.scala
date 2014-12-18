package com.tw.workshop.tdd

import java.text.DecimalFormat

object FilmClassifySystemExtensions {
  implicit class DoubleHelper(number: Double) {
    def withDecimalDigits(decimalNumber: Int = 2) =
      new DecimalFormat("#." + "0"*decimalNumber).format(number).toDouble
  }
}