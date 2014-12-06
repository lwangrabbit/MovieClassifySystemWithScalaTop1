package com.tw.workshop.tdd

/**
 * Created by root on 14-12-6.
 */
class FilmClassifySystem {
  private var films: List[Film] = List()

  def addFilm(filmName: String) {
    films = Film(filmName) :: films
  }

  def getFilm(name: String): Option[Film] = {
    val film = films.find(f => f.filmName == name)
    if (film.isDefined) film else None
  }

}


object Film {
  def apply(filmName: String) = {
    new Film(filmName)
  }
}

class Film(val filmName: String) {

}
