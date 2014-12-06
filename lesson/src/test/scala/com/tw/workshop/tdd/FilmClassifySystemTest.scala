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
        acquiredFilm should not be(None)
        acquiredFilm.get.filmName should be (filmName)
      }

      it("should fail when film name contains invalid char (not [a-zA-Z0-9 ])") {
        val filmName = "The film with invalid char(#)"
        val acquiredFilm = addFilm(filmName).getFilmByName(filmName)
        acquiredFilm should be(None)
      }

      it("should fail when film name is empty") {
        val filmName = ""
        val acquiredFilm = addFilm(filmName).getFilmByName(filmName)
        acquiredFilm should be(None)
      }

      it("should fail when film already existed") {
        val filmName = "The film already existed"
        val acquiredFilms = addFilm(filmName).addFilm(filmName).listFilm
        acquiredFilms.length should be(1)
        acquiredFilms.head.filmName should be (filmName)
      }
    }

    describe("Modify Film Name |") {
      it("should succeed when film name is valid and unique") {
        val (originalFilmName, modifiedFilmName) = ("The original film name", "The modified film name")
        val acquiredFilm = addFilm(originalFilmName)
                          .modifyFilmName(originalFilmName, modifiedFilmName)
                          .getFilmByName(modifiedFilmName)
        acquiredFilm should not be (None)
        acquiredFilm.get.filmName should be(modifiedFilmName)
      }

      it("should fail when film name is valid but existed") {
        val (existedFilmName, originalFilmName) = ("The existed film name", "The original film name")
        val acquiredFilms = addFilm(existedFilmName).addFilm(originalFilmName)
                           .modifyFilmName(originalFilmName, existedFilmName)
                           .listFilm
        acquiredFilms.length should be(2)
        acquiredFilms.exists(existedFilmName == _.filmName) should be (true)
        acquiredFilms.exists(originalFilmName == _.filmName) should be (true)
      }
    }

    describe("Add Film with Category |") {
      it("should succeed when category is valid") {
        val filmNames = List("The film 1", "The film 2", "The film 3", "The film 4")
        val categories = List("OTHER", "HUMOR", "SCIENCE", "LOVE")
        filmNames.zip(categories).foreach( para => {
          val (filmName, filmCategory) = (para._1, para._2)
          val acquiredFilm = addFilm(filmName, filmCategory).getFilmByName(filmName)
          acquiredFilm should not be(None)
          acquiredFilm.get.category should be (filmCategory)}
        )
      }

      it("should succeed when category is not given") {
        val filmName = "The film without category"
        val acquiredFilm = addFilm(filmName).getFilmByName(filmName)
        acquiredFilm should not be(None)
        acquiredFilm.get.category should be ("OTHER")
      }

      it("should fail when category is invalid") {
        val (filmName, filmCategory) = ("The film with invalid category", "HUMORxx")
        val acquiredFilm = addFilm(filmName, filmCategory).getFilmByName(filmName)
        acquiredFilm should be(None)
      }
    }

    describe("Modify Film Category |") {
      it("should succeed when category is valid") {
        val (filmName, filmCategory) = ("The film with valid category", "HUMOR")
        val acquiredFilm = addFilm(filmName)
                          .modifyFilmCategory(filmName, filmCategory)
                          .getFilmByName(filmName)
        acquiredFilm should not be(None)
        acquiredFilm.get.category should be (filmCategory)
      }

      it("should fail when category is invalid") {
        val (filmName, filmCategory) = ("The film with invalid category", "HUMORxx")
        val acquiredFilm = addFilm(filmName)
                          .modifyFilmCategory(filmName, filmCategory)
                          .getFilmByName(filmName)
        acquiredFilm should not be(None)
        acquiredFilm.get.category should be ("OTHER")
      }
    }



  }
}

