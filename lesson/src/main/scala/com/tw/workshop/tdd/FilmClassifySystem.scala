package com.tw.workshop.tdd

/**
 * Created by root on 14-12-6.
 */
class FilmClassifySystem {
  private var films: List[Film] = List()

  def addFilm(name: String) {
    if (isValidate(name)) {
      films = new Film(name) :: films
    }
  }

  def getFilmByName(name: String) = films.find(f => f.filmName == name)

  private def isValidate(name: String) = name == """[a-zA-Z0-9 ]+""".r.findFirstIn(name).getOrElse("")


}

