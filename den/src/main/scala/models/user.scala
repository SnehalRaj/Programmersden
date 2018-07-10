package models

import pgdriver.MyPostgresProfile.api._

//import slick.driver.PostgresDriver.api._




case class User(
    id : Int,
    name: String,
    email: String,
    points: Int,
    solved: List[Int],
    upvoted: List[Int],
    rights: String
)

  class Users(tag: Tag) extends Table[User](tag, "USERS") {

    def id = column[Int]("ID", O.PrimaryKey,O.AutoInc)

    def name = column[String]("NAME")

    def email = column[String]("EMAIL", O.Unique)

    def points = column[Int]("POINTS")
   
    def solved = column[List[Int]]("SOLVED")

    def upvoted = column[List[Int]]("UPVOTED")

    def rights = column[String]("RIGHTS")

    def * =
      (
        id,
        name,
        email,
        points,
        solved,
        upvoted,
        rights
      ).mapTo[User] 
  }