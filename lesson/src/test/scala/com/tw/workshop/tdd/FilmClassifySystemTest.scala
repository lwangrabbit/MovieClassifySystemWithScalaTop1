package com.tw.workshop.tdd

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

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
        getFilmName(acquiredFilm) should be (filmName)
      }

      it("should fail when film name contains invalid char (not [a-zA-Z0-9 ])") {
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
        val acquiredFilms = addFilm(filmName).addFilm(filmName).listFilm
        acquiredFilms.length should be(1)
        isFilmsContainName(acquiredFilms, filmName) should be (true)
      }
    }

    describe("Modify Film Name |") {
      it("should succeed when film name is valid and unique") {
        val (originalFilmName, modifiedFilmName) = ("The original film name", "The modified film name")
        val acquiredFilm = addFilm(originalFilmName)
                          .modifyFilmName(originalFilmName, modifiedFilmName)
                          .getFilmByName(modifiedFilmName)
        getFilmName(acquiredFilm) should be (modifiedFilmName)
      }

      it("should fail when film name is valid but existed") {
        val (existedFilmName, originalFilmName) = ("The existed film name", "The original film name")
        val acquiredFilms = addFilm(existedFilmName).addFilm(originalFilmName)
                           .modifyFilmName(originalFilmName, existedFilmName)
                           .listFilm
        acquiredFilms.length should be(2)
        isFilmsContainName(acquiredFilms, existedFilmName) should be (true)
        isFilmsContainName(acquiredFilms, originalFilmName) should be (true)
      }
    }

    describe("Add Film with Category |") {
      it("should succeed when category is valid") {
        categoryRules.categories.foreach(category => {
          val (filmName, filmCategory) = ("The Film " + category, category)
          val acquiredFilm = addFilm(filmName, filmCategory).getFilmByName(filmName)
          getFilmCategory(acquiredFilm) should be (filmCategory)
        })
      }

      it("should succeed when category is not given") {
        val filmName = "The film without category"
        val acquiredFilm = addFilm(filmName).getFilmByName(filmName)
        getFilmCategory(acquiredFilm) should be (categoryRules.defaultCategory)
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
        getFilmCategory(acquiredFilm) should be (filmCategory)
      }

      it("should fail when category is invalid") {
        val (filmName, filmCategory) = ("The film with invalid category", "HUMORxx")
        val acquiredFilm = addFilm(filmName)
                          .modifyFilmCategory(filmName, filmCategory)
                          .getFilmByName(filmName)
        getFilmCategory(acquiredFilm) should be (categoryRules.defaultCategory)
      }
    }

    describe("List Film By Category |") {
      it("should succeed when category is valid") {
        val filmNames = List("The film other", "The film humor 1", "The film humor 2", "The film love")
        val categories = List("OTHER", "HUMOR", "HUMOR", "LOVE")
        filmNames.zip(categories).foreach( para => {
          val (filmName, filmCategory) = (para._1, para._2)
          addFilm(filmName, filmCategory)})

        listFilm.length should be (filmNames.length)

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
        categoryRules.categories.foreach( listFilmByCategory(_).length should be (0) )
      }

      it("should empty when category is invalid") {
        categoryRules.categories.foreach(category => {
          val (filmName, filmCategory) = ("The Film " + category, category)
          addFilm(filmName, filmCategory)
        })
        listFilmByCategory("HUMORxx").length should be (0)
      }
    }

  }

  private def getFilmName(film: Option[Film]) = film.fold("")(_.name)

  private def getFilmCategory(film: Option[Film]) = film.fold("")(_.category)

  private def isFilmExist(film: Option[Film]) = film.fold(false)(_ => true)

  private def isFilmsContainName(films: List[Film], name: String) = {
    if (films.exists(name == _.name)) true else false
  }


}

