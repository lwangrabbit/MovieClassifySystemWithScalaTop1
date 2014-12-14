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
  val separatorInScore = ","
}

class FilmRepositoryFile() extends FilmRepository{
  val filmFormat = """([\w ]+)\|(\w+)(\|.*)*""".r
  val filmScoreFormat = """(\d)(,[\w ]+)?""".r

  override def persistent(films: List[Film], fileName: String) = {
    val pw = new PrintWriter(fileName)
    films.foreach(film => pw.write(formatFilm(film) + "\n"))
    pw.close()
  }

  override def load(fileName: String) = {
    Source.fromFile(fileName).getLines().map(genFilmMetaStructure).toList
  }

  def formatFilm(film: Film) = {
    val scoreRecords = film.scoreHistory.map(scoreRecord =>
      if (ScoreCfg.defaultComment == scoreRecord.comment) scoreRecord.score
      else List(scoreRecord.score, scoreRecord.comment).mkString(separatorInScore)
    ).mkString(separator)

    if (scoreRecords.isEmpty) List(film.name, film.category).mkString(separator)
    else List(film.name, film.category, scoreRecords).mkString(separator)
  }

  def genFilmMetaStructure(fileRecord: String) = {
    var (filmName, filmCategory, filmScoreHistory) = ("", "", List[FilmScoreRecord]())
    if (!fileRecord.equalsIgnoreCase("") && !fileRecord.endsWith(separator)) {
    fileRecord match {
        case filmFormat(name, category, score) => {
          filmName = name
          filmCategory = category
          if (null != score) filmScoreHistory = genScoreRecords(score)
        }
        case _ => {}
      }
    }
    new FilmStructureInRepository(filmName, filmCategory, filmScoreHistory)
  }

  private def genScoreRecords(scoreRecords: String) = {
    scoreRecords.replaceFirst(separator, "-").replace("-" + separator, "").split(separator.toCharArray).toList.reverse.map(scoreRecord => {
      scoreRecord match {
        case filmScoreFormat(score, comment) =>
          FilmScoreRecord(score.toInt, if (null == comment) ScoreCfg.defaultComment else comment.replaceFirst(separatorInScore, ""))
        case _ =>
          FilmScoreRecord(-1, ScoreCfg.defaultComment)
      }
    })
  }
}

class FilmStructureInRepository(val name: String, val category: String, val scoreHistory: List[FilmScoreRecord])
