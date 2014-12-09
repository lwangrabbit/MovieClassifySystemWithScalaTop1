package com.tw.workshop.tdd

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.io.Source

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
        val filmNames = List("The film other", "The film humor 1", "The film humor 2", "The film love")
        val categories = List("OTHER", "HUMOR", "HUMOR", "LOVE")
        filmNames.zip(categories).foreach( para => {
          val (filmName, filmCategory) = (para._1, para._2)
          addFilm(filmName, filmCategory)})

        listFilms.length should be (categories.length)

        var acquiredFilms = listFilmByCategory("OTHER")
        acquiredFilms.length should be (categories.count("OTHER"==_))
        isFilmsContainName(acquiredFilms, "The film other") should be (true)

        acquiredFilms = listFilmByCategory("HUMOR")
        acquiredFilms.length should be (categories.count("HUMOR"==_))
        isFilmsContainName(acquiredFilms, "The film humor 1") should be (true)
        isFilmsContainName(acquiredFilms, "The film humor 2") should be (true)

        acquiredFilms = listFilmByCategory("LOVE")
        acquiredFilms.length should be (categories.count("LOVE"==_))
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
      it("should succeed when score is valid") {
        defaultScores.foreach(score => {
          val (filmName, filmScore) = ("The Film " + score, score)
          addFilm(filmName).scoreFilm(filmName, filmScore)
          getScoreOfFilm(getFilmByName(filmName)) should be (filmScore)
        })
      }

      it("should succeed when score film many times") {
        val (filmName, filmScore1, filmScore2) = ("The film to score", 1, 2)
        addFilm(filmName).scoreFilm(filmName, filmScore1).scoreFilm(filmName, filmScore2)
        getScoreOfFilm(getFilmByName(filmName)) should be (filmScore2)
      }

      it("should no score when not score film") {
        val filmName = "The film without score"
        addFilm(filmName)
        getScoreOfFilm(getFilmByName(filmName)) should be (defaultUnScore)
      }
    }

    describe("Film Repository|") {
      it("should succeed when persistent films") {
        getFilmMetaRecords().foreach(rec => { addFilm(rec.name, rec.category).scoreFilm(rec.name, rec.score) } )
        persistentFilms()
        isFilmsRepositoryCorrect() should be (true)
      }

      it("should succeed when load films") {
        loadFilms().persistentFilms()
        isFilmsRepositoryCorrect() should be (true)
      }

      it("should succeed when load films with ill-formed") {
        loadFilms(filmsFileSampleIllFormed).persistentFilms()
        isFilmsRepositoryCorrect() should be (true)
      }

      it("should succeed when load films and then add films") {
        loadFilms()
        getDiffRecordsOfFiles().foreach(rec => { addFilm(rec.name, rec.category).scoreFilm(rec.name, rec.score) } )
        persistentFilms()
        isFilmsRepositoryCorrect(sampleFileName = filmsFileSampleAddition) should be (true)
      }

      it("should succeed when add films and then load films") {
        getDiffRecordsOfFiles().foreach(rec => { addFilm(rec.name, rec.category).scoreFilm(rec.name, rec.score) } )
        loadFilms()
        persistentFilms()
        isFilmsRepositoryCorrect(sampleFileName = filmsFileSampleAddition) should be (true)
      }
    }
  }

  private def getNameOfFilm(film: Option[Film]) = film.fold("")(_.name)

  private def getCategoryOfFilm(film: Option[Film]) = film.fold("")(_.category)

  private def getScoreOfFilm(film: Option[Film]) = film.fold(0)(_.score)

  private def isFilmExist(film: Option[Film]) = film.fold(false)(_ => true)

  private def isFilmsContainName(films: List[Film], name: String) = {
    if (films.exists(name == _.name)) true else false
  }

  private def isFilmsRepositoryCorrect(sampleFileName: String = filmsFileSample,
                                       targetFileName: String = filmsFileForPersistent) = {
    val sampleContents = Source.fromFile(sampleFileName).getLines().toSet
    val targetContents = Source.fromFile(targetFileName).getLines().toSet
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

