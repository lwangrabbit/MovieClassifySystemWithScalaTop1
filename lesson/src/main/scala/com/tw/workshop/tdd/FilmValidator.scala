package com.tw.workshop.tdd

/**
 * Created by root on 14-12-7.
 */
class FilmValidator() {
  val validators: Map[String, (String, List[Film]) => Boolean] = //ToDo: generic not thoroughly
    Map("name" -> nameValidator,
        "category" -> categoryValidator,
        "score" -> scoreValidator)

  def nameValidator(name: String, films: List[Film]) = {
    val nameValidator = new ValueValidator(List(
      new NameInvalidCharValidator(), new NameEmptyValidator(), new NameDuplicateValidator(films)))
    nameValidator.validate(name)
  }

  def categoryValidator(category: String, films: List[Film]) = {
    val categoryValidator = new ValueValidator(List(
      new CategoryValidValidator()))
    categoryValidator.validate(category)
  }

  def scoreValidator(score: String, films: List[Film]) = {
    val scoreValidator = new ValueValidator(List(
      new ScoreValidValidator()))
    scoreValidator.validate(score.toInt)
  }


}
