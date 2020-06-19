package tax.calculator.input

import java.io.FileNotFoundException

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatestplus.junit.JUnitRunner
import tax.calculator.model.{IncomeDetails, TaxSlab}

@RunWith(classOf[JUnitRunner])
class FileReaderTest extends FunSuite {

  test("Read valid input file") {
    val incomeDetails: List[IncomeDetails] = new FileReader().readInputFile("src/main/resources/input.txt")
    assert(3 == incomeDetails.length)
    assert(2019 == incomeDetails.head.year)
    assert(2020 == incomeDetails(1).year)
    assert(2020 == incomeDetails(2).year)
  }


  test("throw error for invalid input file") {
    assertThrows[FileNotFoundException] {
      new FileReader().readInputFile("src/main/resources/input_abc.txt")
    }
  }

  test("Read valid tax slab file 2018") {
    val taxSlabs: List[TaxSlab] = new FileReader().readTaxSlabs("2018.csv")
    assert(4 == taxSlabs.length)
    assert(100000 == taxSlabs.head.max_inc_range)
    assert(1 == taxSlabs.head.min_inc_range)
    assert(0 == taxSlabs.head.tax_percentage)

    assert(500000 == taxSlabs(1).max_inc_range)
    assert(100001 == taxSlabs(1).min_inc_range)
    assert(10 == taxSlabs(1).tax_percentage)
  }


  test("throw error for invalid tax slab input file") {
    assertThrows[FileNotFoundException] {
      new FileReader().readTaxSlabs("invalid")
    }
  }
}
