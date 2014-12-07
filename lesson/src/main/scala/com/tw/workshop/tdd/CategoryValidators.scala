package com.tw.workshop.tdd

/**
 * Created by root on 14-12-7.
 */

class CategoryValidValidator() extends Validator {
  private val categories = List("OTHER", "HUMOR", "SCIENCE", "LOVE") //ToDo:Enum?
  override def validate(category: String) = { categories.contains(category) }
}
