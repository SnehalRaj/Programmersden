package DAO
import models._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.stream.scaladsl.Flow
import akka.stream.scaladsl._
import slick.dbio
import slick.dbio.{Effect, NoStream}
import slick.lifted.TableQuery
import slick.sql.{FixedSqlAction, SqlAction, FixedSqlStreamingAction}

//import slick.driver.PostgresDriver.api._
import slick.jdbc.PostgresProfile.api._



import scala.concurrent.ExecutionContext
import scala.concurrent.{Await, Future}

trait Dao   {

   val users = TableQuery[Users]
   val questions = TableQuery[Questions]
   
     val db = Database.forConfig("database")
   
    implicit val session: Session = db.createSession()
/*
    protected implicit def executeFromDb[A](action: SqlAction[A, NoStream, _ <: slick.dbio.Effect]): Future[A] = {
    db.run(action)
	}

    protected implicit def executeFromDB[A](action: FixedSqlAction[A, NoStream, _ <: slick.dbio.Effect]): Future[A] = {
    db.run(action)
    }
	
	protected implicit def executeReadStreamFromDb[A](action: FixedSqlStreamingAction[Seq[A], A, _ <: slick.dbio.Effect]): Future[Seq[A]] = {
    db.run(action)
  }*/

}


object Base extends Dao {

	val connectionUrl = "jdbc:postgresql://localhost/"

  def createQuestion(question: Question): Future[Int] = {
    
      db.run(questions += question )
    }

  def getQuestion(id: Long): Future[Seq[Question]] =  {
    		db.run((for {
			q <- questions.filter(_.id=== id )
		} yield q).result)
  }

  def updateQuestion(id: Long, update: QuestionUpdate): Future[Int] = {


val query = questions.
    filter(_.id === id).
    map(question => (question.title, question.content, question.solution))

val action : DBIO[Int] =
  query.update((update.title,update.content, update.solution))

  db.run(action)
  } 
  def deleteQuestion(id: Long): Future[Int] =  {
  val remove: DBIO[Int] =
  questions.filter(_.id === id).delete

  db.run(remove)
  }
}