import scala.slick.driver.PostgresDriver.simple._

object Main {

  // this is a class that represents the table I've created in the database
//   class Users(tag: Tag) extends Table[(Int, String)](tag, "users") {
//     def id = column[Int]("id")
//     def username = column[String]("username")
//     def * = (id, username)
//   }
case class User(
    id : Long,
    name: String,
    email: String,
    points: Int,
    solved: List[Long],
    upvoted: List[Long],
    rights: String
)

  class Users(tag: Tag) extends Table[User](tag, "Users") {

    def id = column[Long]("ID", O.PrimaryKey)

    def name = column[String]("NAME")

    def email = column[String]("EMAIL", O.Unique)

    def points = column[Int]("POINTS")

    def solved = column[List[Long]]("SOLVED")

    def upvoted = column[List[Long]]("UPVOTED")

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
      ) <> ((User.apply _).tupled, User.unapply)
  }

  def main(args: Array[String]): Unit = {

    // my database server is located on the localhost
    // database name is "my-db"
    // username is "postgres"
    // and password is "postgres"
    val connectionUrl = "jdbc:postgresql://localhost/my2db?user=postgres&password=postgres"

    Database.forURL(connectionUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val users = TableQuery[Users]

        // SELECT * FROM users
        users.list foreach { row =>
          println("user with id " + row._1 + " has username " + row._2)
        }

        // SELECT * FROM users WHERE username='john'
        // users.filter(_.username === "john").list foreach { row => 
        //    println("user whose username is 'john' has id "+row._1 )
        }
    }
  }
}
