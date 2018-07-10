package models

import pgdriver.MyPostgresProfile.api._

case class QuestionUpdate(
title : String,
content: String,
solution: String
)
