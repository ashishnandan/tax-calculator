package tax.calculator.service

import tax.calculator.input.FileReader
import tax.calculator.model.TaxCalculatorItem

class TaxCalculatorService {

  def get_calculated_tax(year: Int, age: Int, income: Double, investment: Double): Double = {
    val tax_brackets : Seq[TaxCalculatorItem] = FileReader.read(year + ".csv")

  }
}
