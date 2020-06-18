package tax.calculator.input

import tax.calculator.model.TaxCalculatorItem

import scala.collection.mutable.ListBuffer
import scala.io.Source

object FileReader {

  val columnHeaders: Seq[String] = Seq("Min Income", "Max Income", "Tax Percentage")

  def read(fileName: String): List[TaxCalculatorItem] = {
    val items: ListBuffer[TaxCalculatorItem] = ListBuffer[TaxCalculatorItem]()
    for (line <- Source.fromResource(fileName).getLines()) {
      val lineItem: Seq[String] = line.split(",")
      if (!columnHeaders.exists(header => line.contains(header))) {
        val minIncome: Double = lineItem(0).trim.toDouble
        val maxIncome: Double = if (lineItem(1).trim.isEmpty) 0.toDouble else lineItem(1).toDouble
        val taxPercentage: Double = lineItem(2).trim.toDouble
        items += TaxCalculatorItem(minIncome, maxIncome, taxPercentage)
      }
    }
    items.toList
  }
}
