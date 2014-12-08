package com.tw.workshop.tdd

import java.io.PrintWriter

/**
 * Created by root on 14-12-7.
 */
trait FilmRepository {

  def persistent(films: List[Film], fileName: String)

  def load(fileName: String): List[FilmStructureInRepository]

  val separator = "|"

}

class FilmRepositoryFile() extends FilmRepository{

  override def persistent(films: List[Film], fileName: String) = {
    val pw = new PrintWriter(fileName)
    films.foreach(film => pw.write(formatFilm(film) + "\n"))
    pw.close()
  }

  override def load(fileName: String) = {
    List(new FilmStructureInRepository("The film 3", "HUMOR", 3), //ToDo: read from fileName?
      new FilmStructureInRepository("The film 2", "HUMOR", 0),
      new FilmStructureInRepository("The film 1", "OTHER", 0))
  }

  private def formatFilm(film: Film) = {
    List(film.name, film.category, film.score).mkString(separator).replace("|0", "")
  }
}

class FilmStructureInRepository(val name: String, val category: String, val score: Int)
