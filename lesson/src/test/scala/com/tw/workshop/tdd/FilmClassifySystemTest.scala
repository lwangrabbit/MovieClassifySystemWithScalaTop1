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

      it("should fail when film name isn`t valid([a-zA-Z0-9 ])") {
        val filmName = "The film with invalid char(#)"
        val acquiredFilm = addFilm(filmName).getFilmByName(filmName)
        acquiredFilm should be(None)
      }

    }

  }
}

