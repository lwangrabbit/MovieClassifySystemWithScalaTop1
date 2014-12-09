package com.tw.workshop.tdd

import java.io.PrintWriter
import scala.io.Source

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
    Source.fromFile(fileName).getLines().map(genFilmMetaStructure).filter("" != _.name).toList
  }

  def genFilmMetaStructure(record: String) = {
    val filmFormatAll = """([\w ]+)\|(\w+)\|(\d)+""".r
    val filmFormatNoScore = """([\w ]+)\|(\w+)""".r
    record match {
      case filmFormatAll(name, category, score) => new FilmStructureInRepository(name, category, score.toInt)
      case filmFormatNoScore(name, category) => new FilmStructureInRepository(name, category, ScoreCfg.defaultUnScore)
      case _ => new FilmStructureInRepository("", "", ScoreCfg.defaultUnScore)
    }
  }

  private def formatFilm(film: Film) = {
    val unusedSegment = separator + ScoreCfg.defaultUnScore
    List(film.name, film.category, film.score).mkString(separator).replace(unusedSegment, "")
  }
}

class FilmStructureInRepository(val name: String, val category: String, val score: Int)
