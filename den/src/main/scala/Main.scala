package simple.rest

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
case class HealthResponse(health: Health)
 case class SetStatusRequest(health: Health)
 
 trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol{
    implicit val healthFormat = jsonFormat2(Health)
   }


class RequestHandler extends Actor with ActorLogging{
 
  var status: Health = Health("Healthy","Initialized")
 
  def receive: Receive = {
   
    case GetHealthRequest =>
      log.debug("Received GetHealthRequest")
      sender() ! HealthResponse(status)
    case request: SetStatusRequest =>
      log.debug("Updating Status to {}",request.health)
      status = request.health
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
          complete(StatusCodes.OK,s"Everything is ${response.health.status}!")
        case _ =>
          complete(StatusCodes.InternalServerError)
      }
    } ~
    post {
      entity(as[Health]) { statusReport =>
        onSuccess(requestHandler ? SetStatusRequest(statusReport)) {
          case response: HealthResponse =>
            complete(StatusCodes.OK,s"Posted health as ${response.health.status}!")
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