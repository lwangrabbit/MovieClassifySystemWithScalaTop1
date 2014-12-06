package com.tw.workshop.tdd

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterEach, Matchers, FunSpec}

/**
 * Created by root on 12/6/14.
 */
@RunWith(classOf[JUnitRunner])
class FilmClassifySystemTest extends FilmClassifySystemTestPrepare {
  describe("Film Classify System |") {

    describe("Add Film |") {
      it("should succeed when film is valid") {
        val film = "The film with valid name"
        addFilm(film).getFilm(film).isDefined should be(true)
      }

    }

  }
}

trait FilmClassifySystemTestPrepare extends FunSpec with Matchers {
  var filmSystem = new FilmClassifySystem()

  def addFilm(name: String) = {
    filmSystem.addFilm(name)
    this
  }

  def getFilm(name: String) = {
    filmSystem.getFilm(name)
  }
}