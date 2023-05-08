package json_protocol

import model.MinMaxStatisticsResponse
import spray.json.*

object MinMaxStatisticsResponseJsonProtocol extends DefaultJsonProtocol {
  implicit object MinMaxStatisticsResponseJsonFormat extends RootJsonFormat[MinMaxStatisticsResponse] {
    def write(r: MinMaxStatisticsResponse): JsObject =
      JsObject(
        "minCasesAmount" -> JsNumber(r.minCasesAmount),
        "minCasesCountryName" -> JsString(r.minCasesCountryName),
        "minCasesDate" -> JsString(r.minCasesDate),
        "maxCasesAmount" -> JsNumber(r.maxCasesAmount),
        "maxCasesCountryName" -> JsString(r.maxCasesCountryName),
        "maxCasesDate" -> JsString(r.maxCasesDate)
      )

    def read(value: JsValue): MinMaxStatisticsResponse = {
      value.asJsObject.getFields(
        "minCasesAmount", "minCasesCountryName", "minCasesDate", "maxCasesAmount", "maxCasesCountryName", "maxCasesDate")
      match {
        case Seq(
        JsNumber(minCasesAmount),
        JsString(minCasesCountryName),
        JsString(minCasesDate),
        JsNumber(maxCasesAmount),
        JsString(maxCasesCountryName),
        JsString(maxCasesDate)) =>
          new MinMaxStatisticsResponse(minCasesAmount.toInt, minCasesCountryName, minCasesDate, maxCasesAmount.toInt, maxCasesCountryName, maxCasesDate)

        case _ => throw DeserializationException("JsObject expected")
      }
    }
  }
}
