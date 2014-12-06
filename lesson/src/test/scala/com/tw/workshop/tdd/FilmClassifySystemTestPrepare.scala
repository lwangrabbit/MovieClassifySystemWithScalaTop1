package com.tw.workshop.tdd

import org.scalatest.{BeforeAndAfterEach, Matchers, FunSpec}

/**
 * Created by root on 14-12-6.
 */
trait FilmClassifySystemTestPrepare extends FunSpec with Matchers with BeforeAndAfterEach {
  var filmSystem: FilmClassifySystem = null

  def addFilm(name: String, category: String = "OTHER") = {
    filmSystem.addFilm(name, category)
    this
  }

  def modifyFilmName(originalName: String, modifiedName: String) = {
    filmSystem.modifyFilmName(originalName, modifiedName)
    this
  }

  def modifyFilmCategory(filmName: String, modifiedCategory: String) = {
    filmSystem.modifyFilmCategory(filmName, modifiedCategory)
    this
  }

  def getFilmByName(name: String) = filmSystem.getFilmByName(name)

  def listFilm = filmSystem.listFilm

  def listFilmByCategory(category: String) = filmSystem.listFilmByCategory(category)

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    filmSystem = new FilmClassifySystem()
  }

}
