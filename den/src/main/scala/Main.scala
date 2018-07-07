package simple.rest

import models._
import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.actor.{Actor, ActorLogging, Props}
import scala.io.StdIn
import scala.concurrent.duration._
import akka.util.Timeout
import akka.pattern.ask
import akka.http.scaladsl.unmarshalling.Unmarshal


object RequestHandler {
  def props(): Props = {
    Props(classOf[RequestHandler])
  }
}
 
case class Health(status: String, description: String){  
  require(status == "Healthy" || status == "Degraded" || status == "Critical" || status == "Unknown",
    "status must be one of: [\"Healthy\",\"Degraded\",\"Critical\", or \"Unknown\"]")
}
case object GetHealthRequest
case class HealthResponse(user: User)
 case class SetStatusRequest(user: User)
 
 trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol{
    implicit val healthFormat = jsonFormat2(Health)
    implicit val userFormat = jsonFormat7(User)
   }


class RequestHandler extends Actor with ActorLogging{
 
  var status: User = User(1,"","",2,List.empty,List.empty,"")
 
  def receive: Receive = {
   
    case GetHealthRequest =>
      log.debug("Received GetHealthRequest")
      sender() ! HealthResponse(status)
    case request: SetStatusRequest =>
      log.debug("Updating Status to {}",request.user)
      status = request.user
      sender() ! HealthResponse(status)
  }
}

object MyApplication extends JsonSupport {

  val host = "localhost"
  val port = 8080

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem("simple-rest-system")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher
   
  
   val requestHandler = system.actorOf(RequestHandler.props(),"requestHandler")
 
//Define the route
val route : Route = {
 
  implicit val timeout = Timeout(20.seconds)
 
  path("health") {
    get {
      onSuccess(requestHandler ? GetHealthRequest) {
        case response: HealthResponse =>
          complete(StatusCodes.OK,s"Everything is ${response.user}!")
        case _ =>
          complete(StatusCodes.InternalServerError)
      }
    } ~
    post {//EXAMPLE REQUEST: http -v post "localhost:8080/health" id:='1.0' name:='"Snehal"' email:='"snehalraj808@gmail.com"' points:='100' solved:='[1,2,3]' upvoted:='[1,2,3]' rights:='"admin"'
      entity(as[User]) { statusReport =>
        onSuccess(requestHandler ? SetStatusRequest(statusReport)) {
          case response: HealthResponse =>
            complete(StatusCodes.OK,s"Posted health as ${response.user}!")
          case _ =>
            complete(StatusCodes.InternalServerError)
        }
      }
    }
  }
}

    //Startup, and listen for requests
    val bindingFuture = Http().bindAndHandle(route, host, port)
    println(s"Waiting for requests at http://$host:$port/...\nHit RETURN to terminate")
    StdIn.readLine()

    //Shutdown
    bindingFuture.flatMap(_.unbind())
    system.terminate()
  }
}