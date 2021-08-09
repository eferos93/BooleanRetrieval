package org.information_retrieval.boolean_retrieval

class IRSystem(corpus: LazyList[Movie], invertedIndex: InvertedIndex) {
//  def answerQuery(words: Seq[String]): Seq[Int] = {
////    words.map((invertedIndex.get compose normaliseText)(_))
////      .withFilter(_.isDefined)
////      .reduce()
//
//
//  }
}

object IRSystem {
  def apply(corpus: LazyList[Movie]): IRSystem = {
    val invertedIndex: InvertedIndex = InvertedIndex(corpus)
    new IRSystem(corpus, invertedIndex)
  }
}
