package com.tw.workshop.tdd

/**
 * Created by root on 14-12-6.
 */
class FilmClassifySystem {
  private var films: List[Film] = List()

  def addFilm(name: String) {
    println("name: " + name)
    println("is exist:" + films.exists(name == _.filmName))
    if (!hasInvalidChar(name) && !isEmptyName(name) && !isExisted(name)) {
      films = new Film(name) :: films
    }
  }

  def getFilmByName(name: String) = films.find(name == _.filmName)

  def listFilm = films

  private def hasInvalidChar(name: String) = name != """[a-zA-Z0-9 ]+""".r.findFirstIn(name).getOrElse("")

  private def isEmptyName(name: String) = "" == name

  private def isExisted(name: String) = films.exists(name == _.filmName)
}

