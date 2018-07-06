// package models

// import slick.jdbc.PostgresProfile.api._

// case class User(
//     id : Long,
//     name: String,
//     email: String,
//     points: Int,
//     // solved: List[Long],
//     // upvoted: List[Long],
//     rights: String
// )

// trait UserTable {

//   val users = TableQuery[Users]

//   class Users(tag: Tag) extends Table[User](tag, "USERS") {

//     def id = column[Long]("ID", O.PrimaryKey)

//     def name = column[String]("NAME")

//     def email = column[String]("EMAIL", O.Unique)

//     def points = column[Int]("POINTS")

//     // def solved = column[List[Long]]("SOLVED")

//     // def upvoted = column[List[Long]]("UPVOTED")

//     def rights = column[String]("RIGHTS")

//     def * =
//       (
//         id,
//         name,
//         email,
//         points,
//         solved,
//         upvoted,
//         rights
//       ) <> ((User.apply _).tupled, User.unapply)
//   }
// }