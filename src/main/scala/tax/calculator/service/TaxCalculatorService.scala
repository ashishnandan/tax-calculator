package tax.calculator.service

import tax.calculator.TaxCalculator
import tax.calculator.input.FileReader
import tax.calculator.model.TaxCalculatorItem

import scala.util.control.Breaks._

class TaxCalculatorService(year: Int, age: Int, income: Double, investment: Double) {

  val senior_citizen_year_wise_rebate: Map[Int, Int] = Map(2018 -> 0, 2019 -> 50000, 2020 -> 75000)
  val year_wise_cess_rate: Map[Int, Double] = Map(2018 -> 1, 2019 -> 2, 2020 -> 5)
  val min_tax_for_cess: Double = 500000
  var tax_brackets: List[TaxCalculatorItem] = FileReader.read(year + ".csv")

  def taxSlab: Int = {
    val taxable_inc: Double = income - getDeductionAmount(age, year, investment)
    var counter: Int = 1
    breakable {
      tax_brackets.foreach(tb => if (tb.max_inc_range >= taxable_inc || tb.max_inc_range == 0) break else counter += 1)
    }
    counter
  }

  private def getDeductionAmount(age: Int, year: Int, investment: Double): Double = {
    val is_senior_citizen: Boolean = age >= 60
    val senior_citizen_amount: Double = if (is_senior_citizen) senior_citizen_year_wise_rebate(year) else 0
    investment + senior_citizen_amount
  }

  def calculateTax: Double = {
    val tax_slab: Double = taxSlab
    var counter: Int = 0
    var tax: Double = 0
    var taxable_inc: Double = income - getDeductionAmount(age, year, investment)
    for (tax_bracket <- tax_brackets) {
      if (counter < tax_slab) {
        val amount: Double = if (tax_bracket.max_inc_range == 0) taxable_inc else tax_bracket.max_inc_range - tax_bracket.min_inc_range + 1
        val min_amount_to_deduct: Double = Math.min(taxable_inc, amount)
        tax += TaxCalculator(min_amount_to_deduct, tax_bracket.tax_percentage).taxable_amount()
        taxable_inc = taxable_inc - amount
        counter += 1
      }
    }
    if (tax > min_tax_for_cess) tax += tax * (year_wise_cess_rate(year) / 100)
    tax
  }
}
