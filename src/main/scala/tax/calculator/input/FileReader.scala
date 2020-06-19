package tax.calculator.input

import java.io.FileNotFoundException

import tax.calculator.model.{IncomeDetails, TaxSlab}

import scala.collection.mutable.ListBuffer
import scala.io.{BufferedSource, Source}

class FileReader extends Reader {

  val columnHeaders: Seq[String] = Seq("Min Income", "Max Income", "Tax Percentage")

  override def readTaxSlabs(fileName: String): List[TaxSlab] = {
    val items: ListBuffer[TaxSlab] = ListBuffer[TaxSlab]()
    try {
      val resource: BufferedSource = Source.fromResource(fileName)
      for (line <- resource.getLines()) {
        val lineItem: Seq[String] = line.split(",")
        if (!columnHeaders.exists(header => line.contains(header))) {
          val minIncome: Double = lineItem(0).trim.toDouble
          val maxIncome: Double = if (lineItem(1).trim.isEmpty) 0.toDouble else lineItem(1).toDouble
          val taxPercentage: Double = lineItem(2).trim.toDouble
          items += TaxSlab(minIncome, maxIncome, taxPercentage)
        }
      }
    } catch {
      case e: NullPointerException => throw new FileNotFoundException("Resource file not found")
    }

    items.toList
  }

  override def readInputFile(fileName: String): List[IncomeDetails] = {
    val incomeDetails: ListBuffer[IncomeDetails] = ListBuffer[IncomeDetails]()
    Source.fromFile(fileName).getLines().foreach(line => {
      val lineItems: Array[String] = line.split(",")
      incomeDetails += IncomeDetails(lineItems(0).toInt, lineItems(1).toInt, lineItems(2).toInt, (lineItems(3).toInt))
    })
    incomeDetails.toList
  }
}
