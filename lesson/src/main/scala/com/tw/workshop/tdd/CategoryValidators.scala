package com.tw.workshop.tdd

/**
 * Created by root on 14-12-7.
 */

class CategoryValidValidator(categoryRules: CategoryRules) extends Validator {
  override def validate(category: String) = { categoryRules.categories.contains(category) }
}

class CategoryRules {
  val categories = List("OTHER", "HUMOR", "SCIENCE", "LOVE")
  val defaultCategory = "OTHER"
}
