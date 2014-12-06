package com.tw.workshop.tdd

/**
 * Created by root on 14-12-7.
 */
class CategoryValidator(val validators: List[Validator]) extends Validator{
  def validate(category: String) = validators.forall(v => v.validate(category))
}

class CategoryValidValidator() extends Validator {
  private val categories = List("OTHER", "HUMOR", "SCIENCE", "LOVE")
  override def validate(category: String) = categories.contains(category)
}
