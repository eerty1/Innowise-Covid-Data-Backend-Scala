package data_storage

import model.{CountrySpecificData, MinMaxStatisticsResponse}
import service.CovidDataService
import service.CovidDataService.{collectMinMaxStatisticsForSingleCountry, findMinMaxStatisticsResponse}

object PreTestData {
  def getSingleCountryStatistics: List[CountrySpecificData] = {
    val singleCountryStatistics =
      CountrySpecificData("Czech Republic", 47263, "2023-01-20T00:00:00Z") ::
      CountrySpecificData("Czech Republic", 47275, "2023-01-21T00:00:00Z") ::
      CountrySpecificData("Czech Republic", 47290, "2023-01-22T00:00:00Z") :: Nil
    singleCountryStatistics
  }

  def getMinMaxForSpecificCountryStatistics: List[MinMaxStatisticsResponse] = {
    val minMaxForSpecificCountryStatistic =
        MinMaxStatisticsResponse(45632, "Belarus", "2023-01-20T00:00:00Z",  98754, "Belarus", "2023-02-15T00:00:00Z") ::
        MinMaxStatisticsResponse(75744, "Greece", "2022-06-20T00:00:00Z",  77554, "Greece", "2022-07-03T00:00:00Z") ::
        MinMaxStatisticsResponse(4554, "Norway", "2022-11-13T00:00:00Z",  5032, "Norway", "2022-12-28T00:00:00Z") :: Nil
    minMaxForSpecificCountryStatistic
  }

  def getDataForSuccessfulPostRequest: String = {
    val testRequestBodySuccess =
      """{
          "availableCountries": [
              {
                  "Country": "Belarus",
                  "Slug": "belarus",
                  "ISO2": "BY"
              },
              {
                  "Country": "Russian Federation",
                  "Slug": "russian-federation",
                  "ISO2": "RU"
              }
          ],
          "dateFrom": "2023-01-20T00:00:00Z",
          "dateTo": "2023-03-01T00:00:00Z"
      }"""
    testRequestBodySuccess
  }

  def getDataForFailedPostRequestUnsupportedOperation: String = {
    val testRequestBodyFailUnsupportedOperation =
      """{
            "availableCountries": [
               {
                  "Country": "Cayman Islands",
                  "Slug": "cayman-islands",
                  "ISO2": "KY"
               }
            ],
            "dateFrom": "2023-01-20T00:00:00Z",
            "dateTo": "2023-03-01T00:00:00Z"
         }"""
    testRequestBodyFailUnsupportedOperation
  }

  def getDataForFailedPostRequestTooManyRequests: String = {
    val testRequestBodyFailTooManyRequests =
        """{
             "availableCountries": [
                {
                   "Country": "Belarus",
                   "Slug": "belarus",
                   "ISO2": "BY"
               },
               {
                   "Country": "Russian Federation",
                   "Slug": "russian-federation",
                   "ISO2": "RU"
               },
               {
                   "Country": "Czech Republic",
                   "Slug": "czech-republic",
                   "ISO2": "CZ"
               },
               {
                   "Country": "Jordan",
                   "Slug": "jordan",
                   "ISO2": "JO"
               },
               {
                   "Country": "Latvia",
                   "Slug": "latvia",
                   "ISO2": "LV"
               },
               {
                   "Country": "Albania",
                   "Slug": "albania",
                   "ISO2": "AL"
               }
             ],
             "dateFrom": "2023-01-20T00:00:00Z",
             "dateTo": "2023-03-01T00:00:00Z"
          }"""
    testRequestBodyFailTooManyRequests
  }
}
