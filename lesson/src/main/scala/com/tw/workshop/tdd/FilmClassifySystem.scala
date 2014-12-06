package com.tw.workshop.tdd

/**
 * Created by root on 14-12-6.
 */
class FilmClassifySystem {
  private var films: List[Film] = List()

  def addFilm(name: String) {
    val filmNameValidator = new InputValidator(List(
      new nameInvalidCharValidator(),
      new nameEmptyValidator(),
      new nameDuplicateValidator(films)))

    if (filmNameValidator.validate(name)) {
      films = new Film(name) :: films
    }
  }

  def getFilmByName(name: String) = films.find(name == _.filmName)

  def listFilm = films

}




