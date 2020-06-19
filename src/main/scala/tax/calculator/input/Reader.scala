package tax.calculator.input

import tax.calculator.model.{IncomeDetails, TaxSlab}

trait Reader {
  def readTaxSlabs(fileName: String): List[TaxSlab]
  def readInputFile(fileName: String): List[IncomeDetails]
}
