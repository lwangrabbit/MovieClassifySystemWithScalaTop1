package com.tw.workshop.tdd

/**
 * Created by root on 14-12-7.
 */
class NameValidator(val validators: List[Validator]) extends Validator{
  def validate(name: String) = validators.forall(v => v.validate(name))
}

class NameInvalidCharValidator() extends Validator {
  override def validate(name: String) = name == """[a-zA-Z0-9 ]+""".r.findFirstIn(name).getOrElse("")
}

class NameEmptyValidator() extends Validator {
  override def validate(name: String) = "" != name
}

class NameDuplicateValidator(films: List[Film]) extends Validator {
  override def validate(name: String) = !films.exists(name == _.filmName)
}
