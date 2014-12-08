package com.tw.workshop.tdd

/**
 * Created by root on 14-12-6.
 */
class Film(var name: String, var category: String) {
  var score: Int = 0

  def updateName(modName: String) =  { name = modName; this }
  def updateCategory(modCategory: String) = { category = modCategory; this }
  def updateScore(modScore: Int) = { score = modScore; this }

}
