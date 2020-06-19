package tax.calculator.model

case class IncomeDetails(year: Int, age: Int, income: Double, investment: Double) {
  override def toString: String = {
    "Year : " ++ year.toString ++ ", Age : " ++ age.toString ++ ", Income : " ++ income.toString ++
      ", Investment : " ++ investment.toString
  }
}
