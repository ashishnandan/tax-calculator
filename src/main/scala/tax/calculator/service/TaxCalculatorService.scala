package tax.calculator.service

import tax.calculator.input.FileReader
import tax.calculator.model.TaxCalculatorItem

import scala.util.control.Breaks._

class TaxCalculatorService(year: Int, age: Int, income: Double, investment: Double) {

  val senior_citizen_year_wise_rebate: Map[Int, Int] = Map(2018 -> 0, 2019 -> 50000, 2020 -> 75000)
  var tax_brackets: List[TaxCalculatorItem] = FileReader.read(year + ".csv")

  def get_tax_slab(): Int = {
    var counter: Int = 1
    val taxable_inc: Double = income - get_deduction_amount(age, year, investment)
    breakable {
      tax_brackets.foreach(tb => if (tb.max_inc_range >= taxable_inc || tb.max_inc_range == 0) break else counter += 1)
    }
    counter
  }

  def get_deduction_amount(age: Int, year: Int, investment: Double): Double = {
    val is_senior_citizen: Boolean = age >= 60
    val senior_citizen_amount: Double = if (is_senior_citizen) senior_citizen_year_wise_rebate(year) else 0
    investment + senior_citizen_amount
  }

}
