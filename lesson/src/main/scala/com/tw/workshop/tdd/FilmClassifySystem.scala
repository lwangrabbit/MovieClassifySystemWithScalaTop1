package com.tw.workshop.tdd

/**
 * Created by root on 14-12-6.
 */
class FilmClassifySystem {
  private var films: List[Film] = List()

  def addFilm(filmName: String) {
    films = new Film(filmName) :: films
  }

  def getFilmByName(name: String) = films.find(f => f.filmName == name)

}

