package org.information_retrieval.boolean_retrieval

import scala.collection.mutable.SortedSet
import scala.util.matching.Regex
import org.information_retrieval.boolean_retrieval.TextUtils._

class InvertedIndex {
  var dictionary: SortedSet[Term] = SortedSet.empty


}

object InvertedIndex {
//  def apply(corpus: LazyList[Movie]): InvertedIndex = {
//    corpus.zipWithIndex.foreach { document =>
//      tokenize(document._1)
//    }
//  }
}