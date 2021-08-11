package org.information_retrieval

import scala.collection.{IterableOps, SortedSet, WithFilter}
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
  
  def query(irSystem: IRSystem, text: String): Unit = {
    irSystem.answerQuery(text.split(" ")).foreach(println(_))
  }

  def queryWithSpellingCorrection(irSystem: IRSystem, text: String): Unit = {
    irSystem.answerQuerywithSpellingCorrection(text.split(" ")).foreach(println(_))
  }

  val editDistance: (String, String) => Int = (word, otherWord) => {
    val numberOfRows = word.length + 1
    val numberOfCols = otherWord.length + 1
    var matrix: Array[Array[Int]] = Array.fill(numberOfRows, numberOfCols)(0)
    (0 to numberOfRows).foreach(index => matrix(index)(0) = index)
    (0 to numberOfCols).foreach(index => matrix(0)(index) = index)
    (1 to numberOfRows).foreach { rowIndex =>
      (1 to numberOfCols).foreach { columnIndex =>
        var candidates = List(matrix(rowIndex - 1)(columnIndex) + 1, matrix(rowIndex)(columnIndex-1) + 1)
        if word.charAt(rowIndex - 1) == otherWord.charAt(columnIndex - 1) then
          candidates = matrix(rowIndex - 1)(columnIndex - 1) +: candidates
        else
          candidates = (matrix(rowIndex - 1)(columnIndex - 1) + 1) +: candidates

        matrix(rowIndex)(columnIndex) = candidates.min
      }
    }
    matrix.last.last
  }

  def findNearestWord[T: Numeric](word: String, distance: (String, String) => T,
                      dictionary: SortedSet[String], keepFirst: Boolean = false): (T, String) = {
    dictionary
      .withFilter(otherWord => keepFirst && word.charAt(0) == word.charAt(0))
      .map(otherWord => (distance(word, otherWord), otherWord))
      .minBy(_._1)
  }
}
