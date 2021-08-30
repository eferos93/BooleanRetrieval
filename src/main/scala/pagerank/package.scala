package org.information_retrieval.boolean_retrieval
import scala.io.Source
import upickle.default.read
package object pagerank {
  def readData() = read[Map[String, List[String]]](os.read(os.pwd/"time"/"example.json"))
}

