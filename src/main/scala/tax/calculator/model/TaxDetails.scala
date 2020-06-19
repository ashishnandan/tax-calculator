package tax.calculator.model

case class TaxDetails(incomeDetails: IncomeDetails, tax: Double, cess: Double, total: Double) {
  override def toString: String = {
    incomeDetails.toString ++ "\nTax : " ++ tax.toString ++ ", Cess : " ++ cess.toString ++ ", Total : " ++
      total.toString
  }
}
