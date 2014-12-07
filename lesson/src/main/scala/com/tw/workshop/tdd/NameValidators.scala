package com.tw.workshop.tdd



class NameInvalidCharValidator() extends Validator {
  override def validate(name: String) = { name == """[a-zA-Z0-9 ]+""".r.findFirstIn(name).getOrElse("") }
}

class NameEmptyValidator() extends Validator {
  override def validate(name: String) = { "" != name }
}

class NameDuplicateValidator(films: List[Film]) extends Validator {
  override def validate(name: String) = { !films.exists(name == _.name) }
}
