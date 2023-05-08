package json_protocol

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import model.{AvailableCountry, CountrySpecificData, RequiredCountries}
import spray.json.*


object CovidDataJsonProtocol extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val availableCountryFormat: RootJsonFormat[AvailableCountry] = jsonFormat3(AvailableCountry.apply)
  implicit val requiredCountriesFormat: RootJsonFormat[RequiredCountries] = jsonFormat3(RequiredCountries.apply)
  implicit val countrySpecificDataFormat: RootJsonFormat[CountrySpecificData] = jsonFormat3(CountrySpecificData.apply)
}
