package com.tw.workshop.tdd


/**
 * Created by root on 14-12-6.
 */
class FilmClassifySystem(filmValidator: FilmValidator, filmRepository: FilmRepository) {
  private var films: List[Film] = List()
  private val validators = filmValidator.validators

  def addFilm(name: String, category: String = CategoryCfg.defaultCategory): Boolean = {
    if (!isFilmValid(name, category)) return false
    films = new Film(name, category) :: films
    true
  }

  def modifyFilmName(originalName: String, modifiedName: String): Boolean = {
    if (!isFilmNameValid(modifiedName)) return false
    getFilmByName(originalName).fold()(_.updateName(modifiedName))
    true
  }

  def modifyFilmCategory(name: String, modifiedCategory: String): Boolean = {
    if (!isFilmCategoryValid(modifiedCategory)) return false
    getFilmByName(name).fold()(_.updateCategory(modifiedCategory))
    true
  }

  def scoreFilm(name: String, score: Int): Boolean = {
    if (!isFilmScoreValid(score)) return false
    getFilmByName(name).fold()(_.updateScore(score))
    true
  }

  def persistentFilms(fileName: String) = {
    filmRepository.persistent(films, fileName)
  }

  def loadFilms(fileName: String) = {
    filmRepository.load(fileName).foreach(f => {
      if (isFilmValid(f.name, f.category)) {
        val film = new Film(f.name, f.category)
        films = film :: films
        if (isFilmScoreValid(f.score)) film.updateScore(f.score)
      }
    })
  }

  def getFilmByName(name: String) = { films.find(name == _.name) }

  def listFilms = films

  def listFilmByCategory(category: String) = { listFilms.filter(category == _.category) }

  private def isFilmValid(name: String, category: String) = {
    validators("name")(name, films) && validators("category")(category, films)
  }

  private def isFilmNameValid(name: String) = validators("name")(name, films)

  private def isFilmCategoryValid(category: String) = validators("category")(category, films)

  private def isFilmScoreValid(score: Int) = validators("score")(score.toString, films)

}







