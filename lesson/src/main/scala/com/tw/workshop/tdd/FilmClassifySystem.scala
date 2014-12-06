package com.tw.workshop.tdd

/**
 * Created by root on 14-12-6.
 */
class FilmClassifySystem {
  private var films: List[Film] = List()

  def addFilm(name: String, category: String = "OTHER") {
    if (isFilmValid(name, category)) {
      films = new Film(name, category) :: films
    }
  }

  def modifyFilmName(originalName: String, modifiedName: String) {
    if (isFilmNameValid(modifiedName)) {
      getFilmByName(originalName).fold()(_.filmName = modifiedName)
    }
  }
  def modifyFilmCategory(filmName: String, modifiedCategory: String) = {
    if (isFilmCategoryValid(modifiedCategory)) {
      getFilmByName(filmName).fold()(_.category = modifiedCategory)
    }
  }

  def getFilmByName(name: String) = films.find(name == _.filmName)

  def listFilm = films

  def listFilmByCategory(category: String) = films.filter(category == _.category)

  private def isFilmValid(name: String, category: String) = {
    isFilmNameValid(name) && isFilmCategoryValid(category)
  }

  private def isFilmNameValid(name: String) = {
    val nameValidator = new NameValidator(List(
          new NameInvalidCharValidator(),
          new NameEmptyValidator(),
          new NameDuplicateValidator(films)))
    nameValidator.validate(name)
  }

  private def isFilmCategoryValid(category: String) = {
    val categoryValidator = new CategoryValidator(List(
          new CategoryValidValidator()))
    categoryValidator.validate(category)
  }
}




