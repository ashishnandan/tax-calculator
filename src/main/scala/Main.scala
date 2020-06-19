
import tax.calculator.input.FileReader
import tax.calculator.model.{IncomeDetails, TaxDetails}
import tax.calculator.service.TaxCalculatorService

import scala.collection.mutable.ListBuffer

object Main {

  def main(args: Array[String]): Unit = {
    tax(new FileReader().readInputFile(args(0)))
  }

  def tax(incomeDetailsList: List[IncomeDetails]): Unit = {
    var response: ListBuffer[TaxDetails] = ListBuffer[TaxDetails]()
    for (income <- incomeDetailsList) {
      val tax: (Double, Double) = new TaxCalculatorService(income.year, income.age, income.income, income.investment).calculateTax
      response += TaxDetails(income, tax._1, tax._2, (tax._1 + tax._2))
    }
    response.foreach { println }
  }

}
