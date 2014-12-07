package com.tw.workshop.tdd

import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, Matchers, FunSpec}

/**
 * Created by root on 14-12-6.
 */
trait FilmClassifySystemTestPrepare extends FunSpec with Matchers with BeforeAndAfterEach with BeforeAndAfterAll{
  var filmSystem: FilmClassifySystem = null
  val filmValidator = new FilmValidator()
  val defaultCategory = filmValidator.categoryRules.defaultCategory
  val defaultCategories = filmValidator.categoryRules.categories
  val defaultScores = filmValidator.scoreRules.scores

  def addFilm(name: String, category: String = defaultCategory) = {
    filmSystem.addFilm(name, category)
    this
  }

  def modifyFilmName(originalName: String, modifiedName: String) = {
    filmSystem.modifyFilmName(originalName, modifiedName)
    this
  }

  def modifyFilmCategory(name: String, modifiedCategory: String) = {
    filmSystem.modifyFilmCategory(name, modifiedCategory)
    this
  }

  def scoreFilm(name: String, score: Int) = {
    filmSystem.scoreFilm(name, score)
    this
  }

  def getFilmByName(name: String) = { filmSystem.getFilmByName(name) }

  def listFilm = { filmSystem.listFilm }

  def listFilmByCategory(category: String) = { filmSystem.listFilmByCategory(category) }

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    filmSystem = new FilmClassifySystem(filmValidator)
  }

}
