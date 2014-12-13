package com.tw.workshop.tdd

import org.scalatest.{BeforeAndAfterEach, Matchers, FunSpec}
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

  def scoreFilm(name: String, score: Int, comment: String = defaultComment) = {
    filmSystem.scoreFilm(name, score, comment)
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

  def listFilms = { filmSystem.listFilmsSortByName }

  def listFilmsSortByName = { filmSystem.listFilmsSortByName }

  def listFilmsSortByScore = { filmSystem.listFilmsSortByScore }

  def listFilmByCategory(category: String) = { filmSystem.listFilmByCategory(category) }

  def getFilmMetaRecords(fileName: String = filmsFileSample) = { filmRepository.load(fileName) }

}

trait FilmClassifySystemTestInit extends FunSpec with Matchers with BeforeAndAfterEach {
  var filmSystem: FilmClassifySystem = null
  val filmValidator = new FilmValidator()
  val filmRepository = new FilmRepositoryFile()

  val defaultCategory = CategoryCfg.defaultCategory
  val defaultCategories = CategoryCfg.categories
  val defaultUnScore = ScoreCfg.defaultUnScore
  val defaultScores = ScoreCfg.scores
  val defaultComment = ScoreCfg.defaultComment

  val filmsFileSample = "FilmsRepository_Sample.txt"
  val filmsFileSampleIllFormed = "FilmsRepository_Sample_ill-formed.txt"
  val filmsFileSampleAddition = "FilmsRepository_Sample_Addition.txt"
  val filmsFileForPersistent = "FilmsRepository_forPersistent.txt"

  private def resetRepositoryFile(fileName: String) = {
    val pw = new PrintWriter(fileName)
    pw.write("\n")
    pw.close()
  }

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    resetRepositoryFile(filmsFileForPersistent)
    filmSystem = new FilmClassifySystem(filmValidator, filmRepository)
  }
}