package com.tw.workshop.tdd

class ScoreValidValidator() extends Validator[Int] {
  override def validate(score: Int) = { ScoreCfg.scores.contains(score) }
}

object ScoreCfg {
  val scores = List(1, 2, 3, 4, 5)
  val defaultUnScore = 0
  val defaultComment = ""
}