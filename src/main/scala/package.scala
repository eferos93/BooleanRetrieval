package org.information_retrieval

import scala.io.Source

package object boolean_retrieval {
  def normaliseText(text: String): String = {
    //    pattern is:
    //    - ^\\w : not a word
    //    - ^\\s : not a  space
    //    - ^- : not a -
    //    .r.replaceAllIn(text, "")
    text.replaceAll("[^\\w^\\s^-]", "").toLowerCase()
  }

  def tokenize(movie: Movie): Array[String] = normaliseText(movie.description).split(" ")

  //  def main(args: Array[String]): Unit = {
  //    println(normaliseText("e.g., this is-a Test"))
  //

  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block // call-by-name
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0) + "ns")
    result
  }

  def read_movie_description(): LazyList[Movie] = {
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
}
