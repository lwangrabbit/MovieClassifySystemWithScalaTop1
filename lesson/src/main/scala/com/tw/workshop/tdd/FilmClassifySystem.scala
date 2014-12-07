package com.tw.workshop.tdd

/**
 * Created by root on 14-12-6.
 */
class FilmClassifySystem(categoryRules: CategoryRules) {
  private var films: List[Film] = List()

  def addFilm(name: String, category: String = categoryRules.defaultCategory) {
    if (isFilmValid(name, category)) {
      films = new Film(name, category) :: films
    }
  }

  def modifyFilmName(originalName: String, modifiedName: String) { //ToDo: return Boolean?
    if (isFilmNameValid(modifiedName)) {
      getFilmByName(originalName).fold()(_.updateName(modifiedName))
    }
  }

  def modifyFilmCategory(filmName: String, modifiedCategory: String) = {
    if (isFilmCategoryValid(modifiedCategory)) {
      getFilmByName(filmName).fold()(_.updateCategory(modifiedCategory))
    }
  }

  def getFilmByName(name: String) = { films.find(name == _.name) }

  def listFilm = films

  def listFilmByCategory(category: String) = { listFilm.filter(category == _.category) }

  private def isFilmValid(name: String, category: String) = {
    isFilmNameValid(name) && isFilmCategoryValid(category)
  }

  private def isFilmNameValid(name: String) = {
    val nameValidator = new ValueValidator(List(
          new NameInvalidCharValidator(),
          new NameEmptyValidator(),
          new NameDuplicateValidator(films)))
    nameValidator.validate(name)
  }

  private def isFilmCategoryValid(category: String) = {
    val categoryValidator = new ValueValidator(List(
          new CategoryValidValidator(categoryRules)))
    categoryValidator.validate(category)
  }
}




