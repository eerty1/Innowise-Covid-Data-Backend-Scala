package route

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes.{InternalServerError, ServiceUnavailable, TooManyRequests}
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, IllegalUriException, StatusCodes}
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.server.{ExceptionHandler, Route}
import akka.http.scaladsl.unmarshalling.Unmarshal
import cors.CORSHandler
import exception.ExceptionService.*
import exception.{APISubscriptionException, ExceptionService}
import json_protocol.CovidDataJsonProtocol.*
import json_protocol.MinMaxStatisticsResponseJsonProtocol.*
import model.{CountrySpecificData, MinMaxStatisticsResponse, RequiredCountries}
import service.CovidDataService.*

import javax.naming.ServiceUnavailableException
import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

object CovidDataRoutes extends CORSHandler {
  private val faultyData = "Covid API provides faulty data on this request. Further actions are terminated."
  private val providerAPITemporaryUnavailableException = "Provider API temporary unavailable, try again later"

  implicit def CovidDataExceptionHandler: ExceptionHandler =
    ExceptionHandler {
      case apiSubscriptionException: APISubscriptionException => complete(HttpResponse(TooManyRequests, entity = apiSubscriptionException.getMessage, headers = corsResponseHeaders))
      case unsupportedOperationException: UnsupportedOperationException => complete(HttpResponse(InternalServerError, entity = faultyData, headers = corsResponseHeaders))
      case serviceUnavailableException: ServiceUnavailableException => complete(HttpResponse(ServiceUnavailable, entity = providerAPITemporaryUnavailableException, headers = corsResponseHeaders))
    }

  def setUpCovidDataRoutes(implicit actorSystem: ActorSystem, executionContext: ExecutionContextExecutor) = {
    val covidDataRoutes = Route.seal (
      path("covid-data") {
        corsHandler(
          get {
            complete {
              Http().singleRequest(HttpRequest(uri = "https://api.covid19api.com/countries"))
            }
          })
      } ~ path("covid-data") {
        corsHandler(
          post {
            entity(as[RequiredCountries]) { requiredCountries =>
              val countriesRequest = Future.traverse(requiredCountries.availableCountries)(availableCountry =>
                Http().singleRequest(
                  HttpRequest(uri = s"https://api.covid19api.com/country/${availableCountry.Slug}/status/confirmed/live?from=${requiredCountries.dateFrom}&to=${requiredCountries.dateTo}"))
                  .flatMap { covidAPIResponse =>
                    checkIfTooManyRequestsExceptionThrown(covidAPIResponse.status)
                    Unmarshal(covidAPIResponse.entity).to[List[CountrySpecificData]]
                  })
              onComplete(countriesRequest.map { countrySpecificData =>
                countrySpecificData.map { countryOneDayStatistics =>
                  collectMinMaxStatisticsForSingleCountry(countryOneDayStatistics)
                }
              }) {
                case Success(result) => complete(findMinMaxStatisticsResponse(result))
                case Failure(exception) => complete(exception)
              }
            }
          })
      })
    covidDataRoutes
  }
}
