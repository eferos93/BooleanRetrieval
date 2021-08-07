package org.information_retrieval.boolean_retrieval

import scala.io.Source
import scala.util.matching.Regex

def read_movie_descrition(): LazyList[Movie] = {
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
  val corpus: LazyList[Movie] = read_movie_descrition()

}
