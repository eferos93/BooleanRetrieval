package org.information_retrieval.boolean_retrieval


object Utils {
  def normaliseText(text: String): String = {
//    pattern is:
//    - ^\\w : not a word
//    - ^\\s : not a  space
//    - ^- : not a -
//    .r.replaceAllIn(text, "")
    text.replaceAll("[^\\w^\\s^-]", "").toLowerCase()
  }

  def tokenize(movie: Movie): Array[String] = {
    normaliseText(movie.description).split(" ")
  }

//  def main(args: Array[String]): Unit = {
//    println(normaliseText("e.g., this is-a Test"))
//  }
}
