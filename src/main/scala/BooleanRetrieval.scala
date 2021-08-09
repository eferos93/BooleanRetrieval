package org.information_retrieval.boolean_retrieval

import scala.io.Source
import scala.util.matching.Regex
import scala.collection.parallel.CollectionConverters._

object BooleanRetrieval extends App {
  val corpus: LazyList[Movie] = time { read_movie_description() }
  val invertedIndex: InvertedIndex = time { InvertedIndex(corpus) }
  println(invertedIndex.toString)
  println(invertedIndex.get("batman"))
}
