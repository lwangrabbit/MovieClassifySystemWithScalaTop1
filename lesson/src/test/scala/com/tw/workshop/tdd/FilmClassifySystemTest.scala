package com.tw.workshop.tdd

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, FunSpec}

/**
 * Created by root on 12/6/14.
 */
@RunWith(classOf[JUnitRunner])
class FilmClassifySystemTest extends FunSpec with Matchers {
  describe("Film Classify System |") {

    describe("Add Film |") {
      it("should succeed when film is valid") {
        val film = "The film with valid name"
        val filmSystem = new FilmClassifySystem()
//        filmSystem.addFilm(film)
        filmSystem.getFilm(film) should be(film)
      }

    }

  }
}