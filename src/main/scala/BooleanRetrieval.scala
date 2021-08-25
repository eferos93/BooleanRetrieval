package org.information_retrieval.boolean_retrieval

import scala.io.Source

object BooleanRetrieval extends App {
  val corpus: LazyList[Movie] = read_movie_description()
  val irSystem: IRSystem = IRSystem(corpus)
//  query(irSystem, "gandalf Frodo")
//  query(irSystem, "homer")
  queryWithSpellingCorrection(irSystem, "fodo")
}
