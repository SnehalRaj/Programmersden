package models

import slick.jdbc.PostgresProfile.api._

case class Questions(
id  : Long ,
title : String,
content: String,
solution: String,
timestamp: Instant,
upvotes: Int,
correctAnswers: Int 
)

2