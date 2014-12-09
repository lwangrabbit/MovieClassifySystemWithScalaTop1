package com.tw.workshop.tdd

/**
 * Created by root on 14-12-6.
 */
class Film(var name: String, var category: String) {
  var score: Int = ScoreCfg.defaultUnScore

  def updateName(modName: String) =  { name = modName; this }
  def updateCategory(modCategory: String) = { category = modCategory; this }
  def updateScore(modScore: Int) = { score = modScore; this }

}
