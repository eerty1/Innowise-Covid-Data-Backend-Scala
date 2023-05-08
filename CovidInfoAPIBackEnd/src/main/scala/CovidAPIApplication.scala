import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import route.CovidDataRoutes
import route.CovidDataRoutes.setUpCovidDataRoutes

import scala.concurrent.ExecutionContextExecutor


object CovidAPIApplication extends App {
  implicit val system: ActorSystem = ActorSystem("covid-api-system")
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  Http().newServerAt("localhost", 8080).bind(setUpCovidDataRoutes)
}
