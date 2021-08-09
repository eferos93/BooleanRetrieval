package org.information_retrieval.boolean_retrieval

import scala.io.Source
import scala.util.matching.Regex
import scala.collection.parallel.CollectionConverters._

def read_movie_descrition(): LazyList[Movie] = {
//  download and extract data from here
//  http://www.cs.cmu.edu/~ark/personas/data/MovieSummaries.tar.gz
  val descriptionsFilePath: String = "MovieSummaries/plot_summaries.txt"
  val movieNamesPath = "MovieSummaries/movie.metadata.tsv"
  val namesTable: Map[String, String] =
    Source.fromFile(movieNamesPath)
      .getLines()
      .map(_.split("\t"))
      .collect{ case line => (line(0), line(2)) }
      .toMap

  Source.fromFile(descriptionsFilePath).getLines()
    .map(_.split("\t"))
    .withFilter(description => namesTable.get(description(0)).isDefined)
    .map(description => Movie(namesTable.get(description(0)).get, description(1)))
    .to(LazyList)
}

object BooleanRetrieval extends App {
  val corpus: LazyList[Movie] = time { read_movie_descrition() }
  val invertedIndex: InvertedIndex = time { InvertedIndex(corpus) }
  println(invertedIndex.toString)
}
