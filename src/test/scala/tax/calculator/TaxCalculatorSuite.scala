package tax.calculator

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TaxCalculatorSuite extends FunSuite {

  test("Calculate tax for a valid input") {
    assert(50000 == new TaxCalculator(500000, 10).taxable_amount())
  }
}
