package tax.calculator.service

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatestplus.junit.JUnitRunner
import tax.calculator.input.FileReader

@RunWith(classOf[JUnitRunner])
class TaxCalculatorServiceTest extends FunSuite{

  val reader: FileReader = new FileReader

  test("get Tax slab for a valid input") {
    assert(3 == new TaxCalculatorService(reader).taxSlab(2018, 30, 750000, 0))
    assert(4 == new TaxCalculatorService(reader).taxSlab(2018, 30, 1250009, 0))
    assert(1 == new TaxCalculatorService(reader).taxSlab(2018, 30, 75000, 0))
    assert(2 == new TaxCalculatorService(reader).taxSlab(2018, 30, 250000, 0))

    assert(3 == new TaxCalculatorService(reader).taxSlab(2019, 30, 750000, 0))
    assert(2 == new TaxCalculatorService(reader).taxSlab(2019, 30, 125000, 0))
    assert(4 == new TaxCalculatorService(reader).taxSlab(2019, 30, 7500000, 0))
    assert(2 == new TaxCalculatorService(reader).taxSlab(2019, 30, 550000, 0))

    assert(2 == new TaxCalculatorService(reader).taxSlab(2019, 60, 625000, 0))
    assert(1 == new TaxCalculatorService(reader).taxSlab(2019, 60, 125000, 0))
    assert(4 == new TaxCalculatorService(reader).taxSlab(2019, 60, 7500000, 0))
    assert(2 == new TaxCalculatorService(reader).taxSlab(2019, 60, 650000, 0))
    assert(3 == new TaxCalculatorService(reader).taxSlab(2019, 40, 650000, 0))

    assert(2 == new TaxCalculatorService(reader).taxSlab(2020, 60, 1275000, 200000))
    assert(3 == new TaxCalculatorService(reader).taxSlab(2020, 50, 1275000, 200000))
  }

  test("get income tax for valid input") {

    val tax1: (Double, Double) = new TaxCalculatorService(reader).calculateTax(2018, 30, 750000, 0)
    assert(90000 == tax1._1)
    assert(0 == tax1._2)

    val tax2: (Double, Double) = new TaxCalculatorService(reader).calculateTax(2018, 30, 1250009, 0)
    assert(215002.7 == tax2._1)
    assert(0 == tax2._2)

    val tax3: (Double, Double) = new TaxCalculatorService(reader).calculateTax(2018, 30, 75000, 0)
    assert(0 == tax3._1)
    assert(0 == tax3._2)

    val tax4: (Double, Double) = new TaxCalculatorService(reader).calculateTax(2018, 30, 250000, 100000)
    assert(5000 == tax4._1)
    assert(0 == tax4._2)

    val tax5: (Double, Double) = new TaxCalculatorService(reader).calculateTax(2018, 30, 250000, 150000)
    assert(5000 == tax5._1)
    assert(0 == tax5._2)

    val tax6: (Double, Double) = new TaxCalculatorService(reader).calculateTax(2019, 66, 7500000, 200000)
    assert(2000000 == tax6._1)
    assert(40000 == tax6._2)

    val tax7: (Double, Double) = new TaxCalculatorService(reader).calculateTax(2019, 56, 7500000, 150000)
    assert(2015000 == tax7._1)
    assert(40300 == tax7._2)

    val tax8: (Double, Double) = new TaxCalculatorService(reader).calculateTax(2019, 66, 1250000, 150000)
    assert(140000 == tax8._1)
    assert(0 == tax8._2)

    val tax9: (Double, Double) = new TaxCalculatorService(reader).calculateTax(2020, 56, 7500000, 0)
    assert(1760000 == tax9._1)
    assert(88000 == tax9._2)

    val tax10: (Double, Double) = new TaxCalculatorService(reader).calculateTax(2020, 66, 7500000, 150000)
    assert(1703750 == tax10._1)
    assert(85187.5 == tax10._2)

    val tax11: (Double, Double) = new TaxCalculatorService(reader).calculateTax(2020, 66, 7500000, 200000)
    assert(1691250 == tax11._1)
    assert(84562.5 == tax11._2)

    val tax12: (Double, Double) = new TaxCalculatorService(reader).calculateTax(2020, 66, 1200000, 200000)
    assert(123750 == tax12._1)
    assert(0 == tax12._2)
  }
}
