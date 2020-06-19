package tax.calculator.service

import tax.calculator.TaxCalculator
import tax.calculator.input.FileReader
import tax.calculator.model.TaxSlab

class TaxCalculator(fileReader: FileReader) {

  val yearSeniorCitizenRebate: Map[Int, Int] = Map(2018 -> 0, 2019 -> 50000, 2020 -> 75000)
  val yearCessRate: Map[Int, Double] = Map(2018 -> 1, 2019 -> 2, 2020 -> 5)
  val maxInvestmentAllowedPerYear: Map[Int, Double] = Map(2018 -> 100000, 2019 -> 150000, 2020 -> 200000)
  val cessThreshold: Double = 500000

  def taxSlab(year: Int, age: Int, income: Double, investment: Double): Int = {
    taxBrackets(year).count(tb => !(tb.max_inc_range >= taxableInc(year, age, income, investment) || tb.max_inc_range == 0)) + 1
  }

  private def taxBrackets(year: Int): List[TaxSlab] = fileReader.readTaxSlabs(year + ".csv")

  private def taxableInc(year: Int, age: Int, income: Double, investment: Double): Double = income - getDeductionAmount(age, year, investment)

  private def getDeductionAmount(age: Int, year: Int, investment: Double): Double = {
    val isSeniorCitizen: Boolean = age >= 60
    val seniorCitizenRebate: Double = if (isSeniorCitizen) yearSeniorCitizenRebate(year) else 0
    Math.min(maxInvestmentAllowedPerYear(year), investment) + seniorCitizenRebate
  }

  def calculateTax(year: Int, age: Int, income: Double, investment: Double): (Double, Double) = {
    var counter: Int = 0
    var tax: Double = 0
    var taxableIncome: Double = income - getDeductionAmount(age, year, investment)
    for (taxBracket <- taxBrackets(year)) {
      if (counter < taxSlab(year, age, income, investment)) {
        // add range by 1 to include min income value
        val amount: Double = if (taxBracket.max_inc_range == 0) taxableIncome else taxBracket.max_inc_range - taxBracket.min_inc_range + 1
        val min_amount_to_deduct: Double = Math.min(taxableIncome, amount)
        tax += TaxCalculator(min_amount_to_deduct, taxBracket.tax_percentage).taxable_amount()
        taxableIncome = taxableIncome - amount
        counter += 1
      }
    }
    (tax, cess(tax, year))
  }

  private def cess(tax: Double, year: Int): Double = {
    if (tax > cessThreshold) tax * (yearCessRate(year) / 100) else 0
  }
}
