package tax.calculator.service

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TaxCalculatorServiceTest extends FunSuite{

  test("get Tax slab for a valid input") {
    assert(3 == new TaxCalculatorService(2018, 30, 750000, 0).get_tax_slab)
    assert(4 == new TaxCalculatorService(2018, 30, 1250009, 0).get_tax_slab)
    assert(1 == new TaxCalculatorService(2018, 30, 75000, 0).get_tax_slab)
    assert(2 == new TaxCalculatorService(2018, 30, 250000, 0).get_tax_slab)

    assert(3 == new TaxCalculatorService(2019, 30, 750000, 0).get_tax_slab)
    assert(2 == new TaxCalculatorService(2019, 30, 125000, 0).get_tax_slab)
    assert(4 == new TaxCalculatorService(2019, 30, 7500000, 0).get_tax_slab)
    assert(2 == new TaxCalculatorService(2019, 30, 550000, 0).get_tax_slab)

    assert(2 == new TaxCalculatorService(2019, 60, 625000, 0).get_tax_slab)
    assert(1 == new TaxCalculatorService(2019, 60, 125000, 0).get_tax_slab)
    assert(4 == new TaxCalculatorService(2019, 60, 7500000, 0).get_tax_slab)
    assert(2 == new TaxCalculatorService(2019, 60, 650000, 0).get_tax_slab)
    assert(3 == new TaxCalculatorService(2019, 40, 650000, 0).get_tax_slab)

    assert(2 == new TaxCalculatorService(2020, 60, 1275000, 200000).get_tax_slab)
    assert(3 == new TaxCalculatorService(2020, 50, 1275000, 200000).get_tax_slab)
  }

  test("get income tax for valid input") {
    assert(90000 == new TaxCalculatorService(2018, 30, 750000, 0).get_tax)
    assert(215002.7 == new TaxCalculatorService(2018, 30, 1250009, 0).get_tax)
    assert(0 == new TaxCalculatorService(2018, 30, 75000, 0).get_tax)
    assert(15000 == new TaxCalculatorService(2018, 30, 250000, 0).get_tax)

    assert(2040000 == new TaxCalculatorService(2019, 66, 7500000, 150000).get_tax)
    assert(2055300 == new TaxCalculatorService(2019, 56, 7500000, 150000).get_tax)
    assert(140000 == new TaxCalculatorService(2019, 66, 1250000, 150000).get_tax)

    assert(1848000 == new TaxCalculatorService(2020, 56, 7500000, 0).get_tax)
    assert(1788937.5 == new TaxCalculatorService(2020, 66, 7500000, 150000).get_tax)
    assert(1775812.5 == new TaxCalculatorService(2020, 66, 7500000, 200000).get_tax)
  }
}
