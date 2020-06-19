package tax.calculator.model

case class TaxSlab(min_inc_range: Double, max_inc_range: Double, tax_percentage: Double) {

  override def toString: String =
    "Min Income Range : " ++ min_inc_range.toString ++
      " Max_Income_Range : " ++ max_inc_range.toString ++ " Tax Percentage " ++ tax_percentage.toString
}

