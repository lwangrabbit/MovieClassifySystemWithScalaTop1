package com.tw.workshop.tdd

/**
 * Created by root on 14-12-6.
 */
trait Validator {
  def validate(name: String): Boolean
}

class InputValidator(val validators: List[Validator]) extends Validator{
  def validate(name: String) = validators.forall(v => v.validate(name))
}

class nameInvalidCharValidator() extends Validator {
  override def validate(name: String) = name == """[a-zA-Z0-9 ]+""".r.findFirstIn(name).getOrElse("")
}

class nameEmptyValidator() extends Validator {
  override def validate(name: String) = "" != name
}

class nameDuplicateValidator(films: List[Film]) extends Validator {
  override def validate(name: String) = !films.exists(name == _.filmName)
}

