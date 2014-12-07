package com.tw.workshop.tdd

/**
 * Created by root on 14-12-7.
 */
class FilmValidator() {
  val categoryRules: CategoryRules = new CategoryRules()
  val validators: Map[String, (String, List[Film]) => Boolean] =
    Map("name" -> nameValidator,
        "category" -> categoryValidator)

  def nameValidator(name: String, films: List[Film]) = {
    val nameValidator = new ValueValidator(List(
      new NameInvalidCharValidator(), new NameEmptyValidator(), new NameDuplicateValidator(films)))
    nameValidator.validate(name)
  }

  def categoryValidator(category: String, films: List[Film]) = {
    val categoryValidator = new ValueValidator(List(
      new CategoryValidValidator(categoryRules)))
    categoryValidator.validate(category)
  }


}
