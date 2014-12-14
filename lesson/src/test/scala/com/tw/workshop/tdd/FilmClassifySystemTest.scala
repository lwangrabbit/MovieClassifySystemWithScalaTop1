package com.tw.workshop.tdd

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.io.Source
import FilmClassifySystemExtensions._

/**
 * Created by root on 12/6/14.
 */
@RunWith(classOf[JUnitRunner])
class FilmClassifySystemTest extends FilmClassifySystemTestPrepare {
  describe("Film Classify System |") {

    describe("Add Film |") {
      it("should succeed when film name is valid") {
        val filmName = "The film with valid name"
        val acquiredFilm = addFilm(filmName).getFilmByName(filmName)
        getNameOfFilm(acquiredFilm) should be (filmName)
      }

      it("should fail when film name contains invalid char ([^a-zA-Z0-9 ])") {
        val filmName = "The film with invalid char(#)"
        val acquiredFilm = addFilm(filmName).getFilmByName(filmName)
        isFilmExist(acquiredFilm) should be(false)
      }

      it("should fail when film name is empty") {
        val filmName = ""
        val acquiredFilm = addFilm(filmName).getFilmByName(filmName)
        isFilmExist(acquiredFilm) should be(false)
      }

      it("should fail when film already existed") {
        val filmName = "The film already existed"
        val acquiredFilms = addFilm(filmName).addFilm(filmName).listFilms
        acquiredFilms.length should be(1)
        isFilmsContainName(acquiredFilms, filmName) should be (true)
      }
    }

    describe("Modify Film Name |") {
      it("should succeed when film name is valid and unique") {
        val (originalFilmName, modifiedFilmName) = ("The original film name", "The modified film name")
        addFilm(originalFilmName).modifyFilmName(originalFilmName, modifiedFilmName)
        isFilmExist(getFilmByName(originalFilmName)) should be(false)
        isFilmExist(getFilmByName(modifiedFilmName)) should be(true)
      }

      it("should fail when film name is valid but existed") {
        val (existedFilmName, toRenameFilmName) = ("The existed film name", "The to rename film name")
        val acquiredFilms = addFilm(existedFilmName).addFilm(toRenameFilmName)
                           .modifyFilmName(toRenameFilmName, existedFilmName)
                           .listFilms
        acquiredFilms.length should be(2)
        isFilmExist(getFilmByName(existedFilmName)) should be(true)
        isFilmExist(getFilmByName(toRenameFilmName)) should be(true)
      }
    }

    describe("Add Film with Category |") {
      it("should succeed when category is valid") {
        defaultCategories.foreach(category => {
          val (filmName, filmCategory) = ("The Film " + category, category)
          val acquiredFilm = addFilm(filmName, filmCategory).getFilmByName(filmName)
          getCategoryOfFilm(acquiredFilm) should be (filmCategory)
        })
      }

      it("should be default category when category is not given") {
        val filmName = "The film without category"
        val acquiredFilm = addFilm(filmName).getFilmByName(filmName)
        getCategoryOfFilm(acquiredFilm) should be (defaultCategory)
      }

      it("should fail when category is invalid") {
        val (filmName, filmCategory) = ("The film with invalid category", "HUMORxx")
        val acquiredFilm = addFilm(filmName, filmCategory).getFilmByName(filmName)
        isFilmExist(acquiredFilm) should be (false)
      }
    }

    describe("Modify Film Category |") {
      it("should succeed when category is valid") {
        val (filmName, filmCategory) = ("The film with valid category", "HUMOR") //ToDo: "HUMOR" should be dynamic
        val acquiredFilm = addFilm(filmName)
                          .modifyFilmCategory(filmName, filmCategory)
                          .getFilmByName(filmName)
        getCategoryOfFilm(acquiredFilm) should be (filmCategory)
      }

      it("should fail when category is invalid") {
        val (filmName, filmCategory) = ("The film with invalid category", "HUMORxx")
        val acquiredFilm = addFilm(filmName)
                          .modifyFilmCategory(filmName, filmCategory)
                          .getFilmByName(filmName)
        getCategoryOfFilm(acquiredFilm) should be (defaultCategory)
      }
    }

    describe("List Film By Category |") {
      it("should succeed when category is valid") {
        val filmParas = List(("The film other", "OTHER"),
                             ("The film humor 1", "HUMOR"),
                             ("The film humor 2", "HUMOR"),
                             ("The film love", "LOVE"))
        filmParas.foreach( para => {
          val (filmName, filmCategory) = (para._1, para._2)
          addFilm(filmName, filmCategory)})

        listFilms.length should be (filmParas.length)

        var acquiredFilms = listFilmByCategory("OTHER")
        acquiredFilms.length should be (1)
        isFilmsContainName(acquiredFilms, "The film other") should be (true)

        acquiredFilms = listFilmByCategory("HUMOR")
        acquiredFilms.length should be (2)
        isFilmsContainName(acquiredFilms, "The film humor 1") should be (true)
        isFilmsContainName(acquiredFilms, "The film humor 2") should be (true)

        acquiredFilms = listFilmByCategory("LOVE")
        acquiredFilms.length should be (1)
        isFilmsContainName(acquiredFilms, "The film love") should be (true)
      }

      it("should empty when category is valid and without film") {
        defaultCategories.foreach( listFilmByCategory(_).length should be (0) )
      }

      it("should empty when category is invalid") {
        defaultCategories.foreach(category => {
          val (filmName, filmCategory) = ("The Film " + category, category)
          addFilm(filmName, filmCategory)
        })
        listFilmByCategory("HUMORxx").length should be (0)
      }
    }

    describe("Film Score |") {
      it("should succeed when score is valid and no comment") {
        defaultScores.foreach(score => {
          val (filmName, filmScore) = ("The film " + score, score)
          addFilm(filmName).scoreFilm(filmName, filmScore)
          getAverageScoreOfFilm(getFilmByName(filmName)) should be (filmScore)
          isFilmContainScoreAndComment(filmName, filmScore) should be (true)
        })
      }

      it("should succeed when score is valid and with comment") {
        defaultScores.foreach(score => {
          val (filmName, filmScore, filmComment) = ("The film " + score, score, "The film comment on " + score)
          addFilm(filmName).scoreFilm(filmName, filmScore, filmComment)
          getAverageScoreOfFilm(getFilmByName(filmName)) should be (filmScore)
          isFilmContainScoreAndComment(filmName, filmScore, filmComment) should be (true)
        })
      }

      it("should fail when comment contains invalid char ([^a-zA-Z0-9 ])") {
        val (filmName, filmScore, filmComment) = ("The film to score", 1, "The film comment $")
        addFilm(filmName).scoreFilm(filmName, filmScore, filmComment)
        getAverageScoreOfFilm(getFilmByName(filmName)) should be (defaultUnScore)
        isFilmContainScoreAndComment(filmName, filmScore, filmComment) should be (false)
      }

      it("should succeed when score film many times") {
        val (filmName, filmScore1, filmComment1, filmScore2, filmComment2) = ("The film to score", 1, "The film comment 1", 2, "The film comment 2")
        addFilm(filmName).scoreFilm(filmName, filmScore1, filmComment1).scoreFilm(filmName, filmScore2, filmComment2)
        getAverageScoreOfFilm(getFilmByName(filmName)) should be(1.5)
        isFilmContainScoreAndComment(filmName, filmScore1, filmComment1) should be (true)
        isFilmContainScoreAndComment(filmName, filmScore2, filmComment2) should be (true)
      }

      it("should have 2 decimal digits if necessary") {
        val (filmName, filmScore1, filmScore2, filmScore3) = ("The film to score", 1, 3, 3)
        addFilm(filmName).scoreFilm(filmName, filmScore1).scoreFilm(filmName, filmScore2).scoreFilm(filmName, filmScore3)
        getAverageScoreOfFilm(getFilmByName(filmName)) should be(2.33)
      }

      it("should no score when not score film") {
        val filmName = "The film without score"
        addFilm(filmName)
        getAverageScoreOfFilm(getFilmByName(filmName)) should be (defaultUnScore)
      }

    }

    describe("List Film |") {
      it("should asc when list score sort by name") {
        List(("The film a to list", 1), ("The film b to list", 2),
             ("The film c to list", 3), ("The film d to list", 4))
          .foreach( para => {
            val (filmName, filmScore) = (para._1, para._2)
            addFilm(filmName).scoreFilm(filmName, filmScore)})

        val acquiredFilms = listFilmsSortByName
        acquiredFilms.length should be(4)
        acquiredFilms(0).name should be("The film a to list")
        acquiredFilms(1).name should be("The film b to list")
        acquiredFilms(2).name should be("The film c to list")
        acquiredFilms(3).name should be("The film d to list")
      }

      it("should desc when list score sort by score") {
        List(("The film a to list", 1), ("The film b to list", 2),
             ("The film c to list", 3), ("The film d to list", 4))
          .foreach( para => {
            val (filmName, filmScore) = (para._1, para._2)
            addFilm(filmName).scoreFilm(filmName, filmScore)})

        val acquiredFilms = listFilmsSortByScore
        acquiredFilms.length should be(4)
        acquiredFilms(0).averageScore should be(4.0)
        acquiredFilms(1).averageScore should be(3.0)
        acquiredFilms(2).averageScore should be(2.0)
        acquiredFilms(3).averageScore should be(1.0)
      }

    }

    describe("Film Repository|") {
      it("should succeed when persistent films") {
        getFilmMetaRecords().foreach(filmRecord => {
          addFilm(filmRecord.name, filmRecord.category)
          filmRecord.scoreHistory.foreach(scoreRecord => scoreFilm(filmRecord.name, scoreRecord.score, scoreRecord.comment) )
        })
        persistentFilms()
        isFilmsRepositoryCorrect() should be (true)
      }

      it("should succeed when load films") {
        loadFilms().persistentFilms()
        isFilmsRepositoryCorrect() should be (true)
      }

      it("should succeed when load films and then add films") {
        loadFilms()
        getDiffRecordsOfFiles().foreach(filmRecord => {
          addFilm(filmRecord.name, filmRecord.category)
          filmRecord.scoreHistory.foreach(scoreRecord => scoreFilm(filmRecord.name, scoreRecord.score, scoreRecord.comment))
        })
        persistentFilms()
        isFilmsRepositoryCorrect(sampleFileName = filmsFileSampleAddition) should be (true)
      }

      it("should succeed when add films and then load films") {
        getDiffRecordsOfFiles().foreach(filmRecord => {
          addFilm(filmRecord.name, filmRecord.category)
          filmRecord.scoreHistory.foreach(scoreRecord => scoreFilm(filmRecord.name, scoreRecord.score, scoreRecord.comment))
        })
        loadFilms()
        persistentFilms()
        isFilmsRepositoryCorrect(sampleFileName = filmsFileSampleAddition) should be (true)
      }

      it("should succeed when load films with ill-formed") {
        loadFilms(filmsFileSampleIllFormed).persistentFilms()
        isFilmsRepositoryCorrect() should be (true)
      }

    }

  }

  private def getNameOfFilm(film: Option[Film]) = film.fold("")(_.name)

  private def getCategoryOfFilm(film: Option[Film]) = film.fold("")(_.category)

  private def getAverageScoreOfFilm(film: Option[Film]) = film.fold(0.0)(_.averageScore)

  private def getScoreHistoryOfFilm(film: Option[Film]) = film.fold(List[FilmScoreRecord]())(_.scoreHistory)

  private def isFilmExist(film: Option[Film]) = film.fold(false)(_ => true)

  private def isFilmsContainName(films: List[Film], name: String) = {
    if (films.exists(name == _.name)) true else false
  }

  private def isFilmContainScoreAndComment(name: String, score: Int, comment: String = defaultComment) = {
    getScoreHistoryOfFilm(getFilmByName(name)).exists(rec => (score == rec.score && comment == rec.comment))
  }


  private def isFilmsRepositoryCorrect(sampleFileName: String = filmsFileSample,
                                       targetFileName: String = filmsFileForPersistent) = {
    val sampleContents = Source.fromFile(sampleFileName).getLines().filter("" != _).toSet
    val targetContents = Source.fromFile(targetFileName).getLines().filter("" != _).toSet
    val intersectSet = targetContents intersect sampleContents
    (targetContents == intersectSet) && (sampleContents == intersectSet)
  }

  private def getDiffRecordsOfFiles(sampleFileName: String = filmsFileSample,
                                    targetFileName: String = filmsFileSampleAddition) = {
    val sampleContents = Source.fromFile(sampleFileName).getLines().toSet
    val targetContents = Source.fromFile(targetFileName).getLines().toSet
    ((sampleContents diff targetContents) ++ (targetContents diff sampleContents))
      .map(filmRepository.genFilmMetaStructure)
  }

}

