package com.tw.workshop.tdd

/**
 * Created by root on 14-12-7.
 */

class CategoryValidValidator() extends Validator[String] {
  override def validate(category: String) = { CategoryCfg.categories.contains(category) }
}

object CategoryCfg {
  val categories = List("OTHER", "HUMOR", "SCIENCE", "LOVE")
  val defaultCategory = "OTHER"
}
