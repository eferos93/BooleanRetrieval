package org.information_retrieval.boolean_retrieval

import java.security.InvalidParameterException

final case class ImpossibleMergeException(private val message: String = "",
                                          private val cause: Throwable = None.orNull) extends Exception(message, cause)

case class Term(term: String, documentId: Int) extends Ordered[Term] {
  var postingList = PostingList(documentId)

  def merge(otherTerm: Term): Unit = {
    otherTerm.term match
      case termString if termString == term => postingList.merge(otherTerm.postingList)
      case _ => throw new ImpossibleMergeException()
  }

  override def compare(that: Term): Int = term compare that.term
}
