package tax.calculator.service

import tax.calculator.TaxCalculator
import tax.calculator.input.FileReader
import tax.calculator.model.TaxSlab

import scala.util.control.Breaks._

class TaxCalculatorService(year: Int, age: Int, income: Double, investment: Double) {

  val yearSeniorCitizenRebate: Map[Int, Int] = Map(2018 -> 0, 2019 -> 50000, 2020 -> 75000)
  val yearCessRate: Map[Int, Double] = Map(2018 -> 1, 2019 -> 2, 2020 -> 5)
  val cessThreshold: Double = 500000
  var tax_brackets: List[TaxSlab] = new FileReader().readTaxSlabs(year + ".csv")

  def taxSlab: Int = {
    val taxableInc: Double = income - getDeductionAmount(age, year, investment)
    var counter: Int = 1
    breakable {
      tax_brackets.foreach(tb => if (tb.max_inc_range >= taxableInc || tb.max_inc_range == 0) break else counter += 1)
    }
    counter
  }

  private def getDeductionAmount(age: Int, year: Int, investment: Double): Double = {
    val isSeniorCitizen: Boolean = age >= 60
    val seniorCitizenRebate: Double = if (isSeniorCitizen) yearSeniorCitizenRebate(year) else 0
    investment + seniorCitizenRebate
  }

  def calculateTax: (Double, Double) = {
    var counter: Int = 0
    var tax: Double = 0
    var taxableIncome: Double = income - getDeductionAmount(age, year, investment)
    for (taxBracket <- tax_brackets) {
      if (counter < taxSlab) {
        // add range by 1 to include min income value
        val amount: Double = if (taxBracket.max_inc_range == 0) taxableIncome else taxBracket.max_inc_range - taxBracket.min_inc_range + 1
        val min_amount_to_deduct: Double = Math.min(taxableIncome, amount)
        tax += TaxCalculator(min_amount_to_deduct, taxBracket.tax_percentage).taxable_amount()
        taxableIncome = taxableIncome - amount
        counter += 1
      }
    }
    (tax, cess(tax))
  }

  def cess(tax: Double): Double = {
    if (tax > cessThreshold) tax * (yearCessRate(year) / 100) else 0
  }
}
