package org.information_retrieval.boolean_retrieval

import scala.io.Source
import scala.util.matching.Regex

def read_movie_descrition(): Unit = {
  val filename: String = "MovieSummaries/plot_summaries.txt"
  val movie_names_file = "MovieSummaries/movie.metadata.tsv"
  val source = Source.fromFile(movie_names_file)
  val namesTable: Map[String, String] =
    source.getLines().map {
      _.split("\t")
        .zipWithIndex
        .filter {
          _ match {
            case (_, 0) | (_, 2) => true
            case _ => false
          }
        }.map(_._1)
    }.collect { case Array(movieCode: String, movieName: String) => (movieCode, movieName)}
     .toMap

}

object BooleanRetrieval extends App {
  read_movie_descrition()
}
