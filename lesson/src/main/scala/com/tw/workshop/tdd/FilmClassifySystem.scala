package com.tw.workshop.tdd


/**
 * Created by root on 14-12-6.
 */
class FilmClassifySystem(filmValidator: FilmValidator, filmRepository: FilmRepository) {
  private var films: List[Film] = List()
  private val validators = filmValidator.validators

  def addFilm(name: String, category: String = filmValidator.categoryRules.defaultCategory) = {
    if (isFilmValid(name, category)) {
      films = new Film(name, category) :: films
    }
  }

  def modifyFilmName(originalName: String, modifiedName: String) = { //ToDo: return Boolean?
    if (isFilmNameValid(modifiedName)) {
      getFilmByName(originalName).fold()(_.updateName(modifiedName))
    }
  }

  def modifyFilmCategory(name: String, modifiedCategory: String) = {
    if (isFilmCategoryValid(modifiedCategory)) {
      getFilmByName(name).fold()(_.updateCategory(modifiedCategory))
    }
  }

  def scoreFilm(name: String, score: Int) = {
    if (isFilmScoreValid(score)) {
      getFilmByName(name).fold()(_.updateScore(score))
    }
  }

  def persistentFilms(fileName: String) = {
    filmRepository.persistent(films, fileName)
  }

  def loadFilms(fileName: String) = {
    filmRepository.load(fileName).foreach(f => {
      if (isFilmValid(f.name, f.category) && isFilmScoreValid(f.score)) {
        films = new Film(f.name, f.category).updateScore(f.score) :: films
      }
    })
  }

  def getFilmByName(name: String) = { films.find(name == _.name) }

  def listFilm = films

  def listFilmByCategory(category: String) = { listFilm.filter(category == _.category) }

  private def isFilmValid(name: String, category: String) = {
    validators("name")(name, films) && validators("category")(category, films)
  }

  private def isFilmNameValid(name: String) = validators("name")(name, films)

  private def isFilmCategoryValid(category: String) = validators("category")(category, films)

  private def isFilmScoreValid(score: Int) = validators("score")(score.toString, films)

}







