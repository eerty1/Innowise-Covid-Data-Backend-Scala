import data_storage.PreTestData.{getSingleCountryStatistics, getMinMaxForSpecificCountryStatistics}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import model.MinMaxStatisticsResponse
import service.CovidDataService.{collectMinMaxStatisticsForSingleCountry, findMinMaxStatisticsResponse}

class CovidAPIServiceTest extends AnyFlatSpec, Matchers {

  "collectMinMaxStatisticsForSingleCountry()" should "return MinMaxStatisticsResponse" +
    "(47263, Czech Republic, 2023-01-20T00:00:00Z, 47290,Czech Republic,2023-01-22T00:00:00Z)" in {
    assert(collectMinMaxStatisticsForSingleCountry(getSingleCountryStatistics).equals(MinMaxStatisticsResponse(47263, "Czech Republic", "2023-01-20T00:00:00Z", 47290, "Czech Republic", "2023-01-22T00:00:00Z")))
  }

  "findMinMaxStatisticsResponse()" should "return MinMaxStatisticsResponse" +
    "(4554, Norway, 2022-11-13T00:00:00Z, 98754,Belarus,2023-02-15T00:00:00Z)" in {
    assert(findMinMaxStatisticsResponse(getMinMaxForSpecificCountryStatistics).equals(MinMaxStatisticsResponse(4554, "Norway", "2022-11-13T00:00:00Z", 98754, "Belarus", "2023-02-15T00:00:00Z")))
  }
}
