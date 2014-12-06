package com.tw.workshop.tdd

import org.scalatest.{BeforeAndAfterEach, Matchers, FunSpec}

/**
 * Created by root on 14-12-6.
 */
trait FilmClassifySystemTestPrepare extends FunSpec with Matchers {
  var filmSystem = new FilmClassifySystem()

  def addFilm(name: String) = {
    filmSystem.addFilm(name)
    this
  }

  def getFilmByName(name: String) = filmSystem.getFilmByName(name)

  def listFilm = filmSystem.listFilm

}
