package com.tw.workshop.tdd

/**
 * Created by root on 14-12-7.
 */


class ScoreValidValidator(scoreRules: ScoreRules) extends Validator[Int] {
  override def validate(score: Int) = { scoreRules.scores.contains(score) }
}

class ScoreRules {
  val scores = List(1, 2, 3, 4, 5)
  val defaultUnScore = 0
}