package com.tw.workshop.tdd

/**
 * Created by root on 14-12-6.
 */
class FilmClassifySystem {
  private var films: List[String] = List()

  def addFilm(filmName: String) {
    films = filmName :: films
  }

  def getFilm(filmName: String) = {
    if (films.exists(f => f == filmName)) filmName else ""
  }

}
