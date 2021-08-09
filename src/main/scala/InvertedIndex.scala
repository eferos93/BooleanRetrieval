package org.information_retrieval.boolean_retrieval

import scala.collection.mutable.SortedSet
import org.information_retrieval.boolean_retrieval.tokenize

class  InvertedIndex {
  var dictionary: SortedSet[Term] = SortedSet.empty
  def apply(token: String): Option[Term] = dictionary.find(_.term == token)

  override def toString: String = s"Dictionary with ${dictionary.size} terms"
}

object InvertedIndex {
  def apply(corpus: LazyList[Movie]): InvertedIndex = {
    var intermidiateDictionary: Map[String, Term] = Map.empty
    corpus.zipWithIndex.foreach { document =>
      tokenize(document._1).foreach { token =>
        intermidiateDictionary.get(token) match {
          case Some(term) => term.merge(Term(token, document._2))
          case None => intermidiateDictionary = intermidiateDictionary + (token -> Term(token, document._2))
        }
      }
    }
    val invertedIndex: InvertedIndex = new InvertedIndex
    invertedIndex.dictionary = intermidiateDictionary.values.to(SortedSet)
    invertedIndex
  }
}