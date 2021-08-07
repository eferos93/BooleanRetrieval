package org.information_retrieval.boolean_retrieval

import scala.io.Source
import scala.util.matching.Regex

def read_movie_descrition(): Unit = {
  val filename: String = "MovieSummaries/plot_summaries.txt"
  val movie_names_file = "MovieSummaries/movie.metadata.tsv"
  val source = Source.fromFile(movie_names_file)
  val namesTable: Map[String, String] =
    source.getLines()
      .map(_.split("\t"))
      .collect{ case line => (line(0), line(2))}
      .toMap
}

object BooleanRetrieval extends App {
//  print(read_movie_descrition())
}
