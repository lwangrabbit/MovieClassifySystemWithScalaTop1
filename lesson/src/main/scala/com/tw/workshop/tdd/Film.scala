package com.tw.workshop.tdd

/**
 * Created by root on 14-12-6.
 */
class Film(var name: String, var category: String) {
  def updateName(modName: String) =  { name = modName }

  def updateCategory(modCategory: String) = { category = modCategory }

}
