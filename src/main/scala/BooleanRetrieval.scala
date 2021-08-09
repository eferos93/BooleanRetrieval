package org.information_retrieval.boolean_retrieval

import scala.io.Source
import scala.util.matching.Regex
import scala.collection.parallel.CollectionConverters._

object BooleanRetrieval extends App {
  val corpus: LazyList[Movie] = read_movie_description()
  val irSystem: IRSystem = IRSystem(corpus)
  query(irSystem, "gandalf Frodo")
  query(irSystem, "homer")
}
