package model

class MinMaxStatisticsResponse (var minCasesAmount: Int = 0, var minCasesCountryName: String = "", var minCasesDate: String = "",
                                var maxCasesAmount: Int = 0, var maxCasesCountryName: String = "", var maxCasesDate: String = "") {

  private def canEqual(other: Any): Boolean = other.isInstanceOf[MinMaxStatisticsResponse]

  override def equals(other: Any): Boolean = other match {
    case that: MinMaxStatisticsResponse =>
      (that canEqual this) &&
        minCasesAmount == that.minCasesAmount &&
        minCasesCountryName == that.minCasesCountryName &&
        minCasesDate == that.minCasesDate &&
        maxCasesAmount == that.maxCasesAmount &&
        maxCasesCountryName == that.maxCasesCountryName &&
        maxCasesDate == that.maxCasesDate
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(minCasesAmount, minCasesCountryName, minCasesDate, maxCasesAmount, maxCasesCountryName, maxCasesDate)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
