package com.tw.workshop.tdd

/**
 * Created by root on 14-12-13.
 */
class CommentValidValidator() extends Validator[String] {
  override def validate(comment: String) = { comment == """[a-zA-Z0-9 ]+""".r.findFirstIn(comment).getOrElse("") }
}
