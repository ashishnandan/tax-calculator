package tax.calculator.service

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TaxCalculatorServiceTest extends FunSuite{

  test("get Tax slab for a valid input") {
    assert(3 == new TaxCalculatorService(2018, 30, 750000, 0).get_tax_slab())
    assert(4 == new TaxCalculatorService(2018, 30, 1250009, 0).get_tax_slab())
    assert(1 == new TaxCalculatorService(2018, 30, 75000, 0).get_tax_slab())
    assert(2 == new TaxCalculatorService(2018, 30, 250000, 0).get_tax_slab())

    assert(3 == new TaxCalculatorService(2019, 30, 750000, 0).get_tax_slab())
    assert(2 == new TaxCalculatorService(2019, 30, 125000, 0).get_tax_slab())
    assert(4 == new TaxCalculatorService(2019, 30, 7500000, 0).get_tax_slab())
    assert(2 == new TaxCalculatorService(2019, 30, 550000, 0).get_tax_slab())

    assert(2 == new TaxCalculatorService(2019, 60, 625000, 0).get_tax_slab())
    assert(1 == new TaxCalculatorService(2019, 60, 125000, 0).get_tax_slab())
    assert(4 == new TaxCalculatorService(2019, 60, 7500000, 0).get_tax_slab())
    assert(2 == new TaxCalculatorService(2019, 60, 650000, 0).get_tax_slab())
    assert(3 == new TaxCalculatorService(2019, 40, 650000, 0).get_tax_slab())

    assert(2 == new TaxCalculatorService(2020, 60, 1275000, 200000).get_tax_slab())
    assert(3 == new TaxCalculatorService(2020, 50, 1275000, 200000).get_tax_slab())
  }
}
