
import java.io.FileNotFoundException

import tax.calculator.input.FileReader
import tax.calculator.model.{IncomeDetails, TaxDetails}
import tax.calculator.service.TaxCalculatorService

import scala.collection.mutable.ListBuffer

object Main {

  def main(args: Array[String]): Unit = {
    try {
      tax(new FileReader().readInputFile(args(0)))
    } catch {
      case e: FileNotFoundException => println(e.getMessage)
      case b: ArrayIndexOutOfBoundsException => println("Input file not correct, some values are missing")
      case c: IllegalArgumentException => println(c.getMessage)
    }
  }

  def tax(incomeDetailsList: List[IncomeDetails]): Unit = {
    var response: ListBuffer[TaxDetails] = ListBuffer[TaxDetails]()
    val taxCalculatorService = new TaxCalculatorService(new FileReader())
    for (income <- incomeDetailsList) {
      val tax: (Double, Double) = taxCalculatorService.calculateTax(income.year, income.age, income.income, income.investment)
      response += TaxDetails(income, tax._1, tax._2, tax._1 + tax._2)
    }
    response.foreach { println }
  }

}