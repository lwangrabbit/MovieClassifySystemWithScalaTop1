package com.tw.workshop.tdd

import FilmClassifySystemExtensions._

class Film(var name: String, var category: String) {
  var averageScore: Double = ScoreCfg.defaultUnScore
  var scoreHistory: List[FilmScoreRecord] = List()

  def updateName(modName: String) =  { name = modName; this }

  def updateCategory(modCategory: String) = { category = modCategory; this }

  def addScore(score: Int, comment: String) = {
    scoreHistory = FilmScoreRecord(score, comment) :: scoreHistory
    averageScore = (scoreHistory.map(_.score).sum/scoreHistory.length.toDouble).withDecimalDigits()
    this
  }
}

case class FilmScoreRecord(val score: Int, val comment: String)