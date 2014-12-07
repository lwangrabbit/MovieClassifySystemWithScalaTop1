package com.tw.workshop.tdd

/**
 * Created by root on 14-12-6.
 */
trait Validator {
  def validate(value: String): Boolean
}

class ValueValidator(val validators: List[Validator]) extends Validator {
  def validate(value: String) = { validators.forall(v => v.validate(value)) }
}

