import akka.http.scaladsl.model.{ContentTypes, HttpMethods, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import model.*
import route.CovidDataRoutes.setUpCovidDataRoutes
import akka.http.scaladsl.unmarshalling.Unmarshal
import json_protocol.CovidDataJsonProtocol.*
import json_protocol.MinMaxStatisticsResponseJsonProtocol.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spray.json.JsonParser
import data_storage.PreTestData.{getDataForSuccessfulPostRequest, getDataForFailedPostRequestUnsupportedOperation, getDataForFailedPostRequestTooManyRequests}


class CovidAPIRoutesTest extends AnyFlatSpec, Matchers, ScalatestRouteTest {
  //API is not free, that is why running the following tests sequentially may lead to failing with '429 Too Many Requests'

  "/covid-data GET" should "return HttpResponse with StatusCode 200 and List[AvailableCountries] inside" in {
    Get("/covid-data") ~> setUpCovidDataRoutes ~> check {
      status shouldEqual StatusCodes.OK
      contentType shouldBe ContentTypes.`application/json`
    }
  }

  "/covid-data POST" should "return HttpResponse with StatusCode 200 and class MinMaxStatisticsResponse inside" in {
    RequestBuilder(HttpMethods.POST)(
      "/covid-data",
      JsonParser(getDataForSuccessfulPostRequest)
    ) ~> setUpCovidDataRoutes ~> check {
      status shouldEqual StatusCodes.OK
      responseAs[MinMaxStatisticsResponse] shouldEqual MinMaxStatisticsResponse(994037, "Belarus", "2023-01-20T00:00:00Z",
                                                                                21974098, "Russian Federation", "2023-03-01T00:00:00Z")
    }
  }

  "/covid-data POST" should "fail with UnsupportedOperationException" in {
    RequestBuilder(HttpMethods.POST)(
      "/covid-data",
      JsonParser(getDataForFailedPostRequestUnsupportedOperation)
    ) ~> setUpCovidDataRoutes ~> check {
      status shouldEqual StatusCodes.InternalServerError
    }
  }

  "/covid-data POST" should "fail with APISubscriptionException" in {
    RequestBuilder(HttpMethods.POST)(
      "/covid-data",
      JsonParser(getDataForFailedPostRequestTooManyRequests)
    ) ~> setUpCovidDataRoutes ~> check {
      status shouldEqual StatusCodes.TooManyRequests
    }
  }
}
