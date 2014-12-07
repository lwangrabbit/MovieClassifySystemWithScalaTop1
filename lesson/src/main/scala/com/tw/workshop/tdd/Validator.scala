package com.tw.workshop.tdd

/**
 * Created by root on 14-12-6.
 */
trait Validator[T] {
  def validate(value: T): Boolean
}

class ValueValidator[T](val validators: List[Validator[T]]) extends Validator[T] {
  def validate(value: T) = { validators.forall(v => v.validate(value)) }
}

