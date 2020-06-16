package tax.calculator.input

import tax.calculator.model.TaxCalculatorItem

import scala.io.Source

object FileReader {

  val columnHeaders: Seq[String] = Seq("Min Income", "Max Income", "Tax Percentage")

  def read(fileName: String): Seq[TaxCalculatorItem] = {
    val items: Vector[TaxCalculatorItem] = Vector[TaxCalculatorItem]()
    for (line <- Source.fromResource(fileName).getLines()) {
      val lineItem: Seq[String] = line.split(",")
      if (!columnHeaders.exists(header => line.contains(header))) {
        val minIncome: Double = lineItem(0).trim.toDouble
        val maxIncome: Double = if (lineItem(1).trim.isEmpty) 10.toDouble else lineItem(2).toDouble
        val taxPercentage: Double = lineItem(2).trim.toDouble
        items :+ TaxCalculatorItem(minIncome, maxIncome, taxPercentage)
      }
    }
    items
  }

  def main(args: Array[String]): Unit = {
    println(read("2018.csv"))
  }
}
