package com.tw.workshop.tdd

class CommentValidValidator() extends Validator[String] {
  override def validate(comment: String) = { comment == """[a-zA-Z0-9 ]+""".r.findFirstIn(comment).getOrElse("") }
}