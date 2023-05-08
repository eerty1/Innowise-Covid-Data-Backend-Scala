package exception

import akka.http.scaladsl.model.StatusCodes.{ServiceUnavailable, TooManyRequests}
import akka.http.scaladsl.model.StatusCode
import model.CountrySpecificData

object ExceptionService {
  private val tooManyRequestsException = "Due to many requests API is blocked. Retry submission in a few seconds or select fewer countries"

  def checkIfTooManyRequestsExceptionThrown(statusCode: StatusCode): Unit = {
    if (statusCode == TooManyRequests) {
      throw new APISubscriptionException(tooManyRequestsException)
    }
  }
}
