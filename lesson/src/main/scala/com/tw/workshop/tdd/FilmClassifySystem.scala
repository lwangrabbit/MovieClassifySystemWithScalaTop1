package com.tw.workshop.tdd

/**
 * Created by root on 14-12-6.
 */
class FilmClassifySystem {
  private var films: List[Film] = List()

  def addFilm(name: String, category: String = "OTHER") {
    if (isFilmNameValid(name)) {
      films = new Film(name, category) :: films
    }
  }

  def modifyFilmName(originalName: String, modifiedName: String) {
    if (isFilmNameValid(modifiedName)) {
      getFilmByName(originalName).fold()(_.filmName = modifiedName)
    }
  }

  def getFilmByName(name: String) = films.find(name == _.filmName)

  def listFilm = films

  private def isFilmNameValid(name: String) = {
    val filmNameValidator = new InputValidator(List(
      new nameInvalidCharValidator(),
      new nameEmptyValidator(),
      new nameDuplicateValidator(films)))

    filmNameValidator.validate(name)
  }
}




