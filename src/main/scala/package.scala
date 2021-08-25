package org.information_retrieval

import scala.collection.{IterableOps, SortedSet, WithFilter}
import scala.io.Source
import scala.math.min


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

  def editDistanceIterative[A](word: Iterable[A], otherWord: Iterable[A]): Int = {
    val numberOfRows = word.size + 1
    val numberOfCols = otherWord.size + 1
    var matrix: Array[Array[Int]] = Array.fill(numberOfRows, numberOfCols)(0)
    (0 to numberOfRows - 1).foreach(index => matrix(index)(0) = index)
    (0 to numberOfCols - 1).foreach(index => matrix(0)(index) = index)
    (1 to numberOfRows - 1).foreach { rowIndex =>
      (1 to numberOfCols - 1).foreach { columnIndex =>
        var candidates = List(matrix(rowIndex - 1)(columnIndex) + 1, matrix(rowIndex)(columnIndex-1) + 1)
        if word.drop(rowIndex - 1).take(1) == otherWord.drop(columnIndex - 1).take(1) then
          candidates = matrix(rowIndex - 1)(columnIndex - 1) +: candidates
        else
          candidates = (matrix(rowIndex - 1)(columnIndex - 1) + 1) +: candidates

        matrix(rowIndex)(columnIndex) = candidates.min
      }
    }
    matrix.last.last
  }

//  way more efficient than iterative solution
  def editDistanceFunctional[A](word: Iterable[A], otherWord: Iterable[A]): Int = {
    word.foldLeft((0 to otherWord.size).toList) { (previous, char) =>
      (previous zip previous.tail zip otherWord).scanLeft(previous.head + 1) {
        case (h, ((d, v), otherChar)) => min(min(h + 1, v + 1), d + (if (char == otherChar) 0 else 1))
      }
    }.last
  }


  def findNearestWord[T: Numeric](word: String, distance: (String, String) => T,
                      dictionary: SortedSet[String], keepFirst: Boolean = false): (T, String) = {
    dictionary
      .withFilter(otherWord => keepFirst && word.charAt(0) == otherWord.charAt(0))
      .map(otherWord => (distance(word, otherWord), otherWord))
      .minBy(_._1)
  }
}
