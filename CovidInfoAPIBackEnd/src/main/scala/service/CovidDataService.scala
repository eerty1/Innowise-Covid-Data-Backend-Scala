package service

import model.{CountrySpecificData, MinMaxStatisticsResponse}

object CovidDataService {

  def collectMinMaxStatisticsForSingleCountry(singleCountryStatistics: List[CountrySpecificData]): MinMaxStatisticsResponse = {
    
    val minMaxResponseBody = MinMaxStatisticsResponse(
      singleCountryStatistics.minBy(_.Cases).Cases,
      singleCountryStatistics.minBy(_.Cases).Country,
      singleCountryStatistics.minBy(_.Cases).Date,
      singleCountryStatistics.maxBy(_.Cases).Cases,
      singleCountryStatistics.maxBy(_.Cases).Country,
      singleCountryStatistics.maxBy(_.Cases).Date)
    
    minMaxResponseBody
  }

  def findMinMaxStatisticsResponse(minMaxForSpecificCountryStatistics: List[MinMaxStatisticsResponse]): MinMaxStatisticsResponse = {

    val minMaxResponseBody = MinMaxStatisticsResponse()

    minMaxForSpecificCountryStatistics.foreach { minMaxStatisticsResponse =>
      if (minMaxResponseBody.maxCasesAmount < minMaxStatisticsResponse.maxCasesAmount) {
        minMaxResponseBody.maxCasesAmount = minMaxStatisticsResponse.maxCasesAmount
        minMaxResponseBody.maxCasesCountryName = minMaxStatisticsResponse.maxCasesCountryName
        minMaxResponseBody.maxCasesDate = minMaxStatisticsResponse.maxCasesDate
      }
      if (minMaxResponseBody.minCasesAmount == 0 | minMaxResponseBody.minCasesAmount > minMaxStatisticsResponse.minCasesAmount) {
        minMaxResponseBody.minCasesAmount = minMaxStatisticsResponse.minCasesAmount
        minMaxResponseBody.minCasesCountryName = minMaxStatisticsResponse.minCasesCountryName
        minMaxResponseBody.minCasesDate = minMaxStatisticsResponse.minCasesDate
      }
    }
    
    minMaxResponseBody
  }
}
