package models

import pgdriver.MyPostgresProfile.api._

case class Question(
id  : Long ,
title : String,
content: String,
solution: String,
upvotes: Int,
correctAnswers: Int 
)

  class Questions(tag: Tag) extends Table[Question](tag, "QUESTIONS") {

    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

    def title = column[String]("TITLE")

    def content = column[String]("CONTENT")

    def solution = column[String]("SOLUTION")
   
    def upvotes = column[Int]("UPVOTES")

    def correctAnswers = column[Int]("CORRECTANSWERS")

    def * =
      (
        id,
        title,
        content,
        solution,
        upvotes,
        correctAnswers
      ).mapTo[Question] 
  }