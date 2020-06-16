package tax.calculator

case class TaxCalculator(amount: Double, tax_percentage: Double) {

  import TaxCalculator._

  def taxable_amount(): Double = calculate(amount, tax_percentage)
}

object TaxCalculator {

  private def calculate(amount: Double, tax_percentage: Double): Double = (amount * tax_percentage) / 100
}
