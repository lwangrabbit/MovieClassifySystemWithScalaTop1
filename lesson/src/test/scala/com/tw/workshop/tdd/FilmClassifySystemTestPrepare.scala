package com.tw.workshop.tdd

import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, Matchers, FunSpec}
import scala.io.Source
import java.io.PrintWriter

/**
 * Created by root on 14-12-6.
 */
trait FilmClassifySystemTestPrepare extends FilmClassifySystemTestInit {


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

  def persistentFilms(fileName: String = filmsFileForPersistent) = {
    filmSystem.persistentFilms(fileName)
    this
  }

  def loadFilms(fileName: String = filmsFileSample) = {
    filmSystem.loadFilms(fileName)
    this
  }

  def getFilmByName(name: String) = { filmSystem.getFilmByName(name) }

  def listFilm = { filmSystem.listFilm }

  def listFilmByCategory(category: String) = { filmSystem.listFilmByCategory(category) }

  def getFilmMetaRecords(fileName: String = filmsFileSample) = {
    List(new FilmStructureInRepository("The film 3", "HUMOR", 3), //ToDo: read from fileName?
      new FilmStructureInRepository("The film 2", "HUMOR", 0),
      new FilmStructureInRepository("The film 1", "OTHER", 0))
  }

}

trait FilmClassifySystemTestInit extends FunSpec with Matchers with BeforeAndAfterEach with BeforeAndAfterAll {
  var filmSystem: FilmClassifySystem = null
  val filmValidator = new FilmValidator()
  val filmRepository = new FilmRepositoryFile()

  val defaultCategory = filmValidator.categoryRules.defaultCategory
  val defaultCategories = filmValidator.categoryRules.categories
  val defaultScores = filmValidator.scoreRules.scores

  val filmsFileSample = "FilmsRepository_Sample.txt"
  val filmsFileForPersistent = "FilmsRepository_forPersistent.txt"

  private def cleanTmpFile(fileName: String) = {
    val pw = new PrintWriter(fileName)
    pw.write("\n")
    pw.close()
  }
  override protected def beforeEach(): Unit = {
    super.beforeEach()
    cleanTmpFile(filmsFileForPersistent)
    filmSystem = new FilmClassifySystem(filmValidator, filmRepository)
  }
}