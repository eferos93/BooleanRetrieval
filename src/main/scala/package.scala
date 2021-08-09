package org.information_retrieval

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

}
