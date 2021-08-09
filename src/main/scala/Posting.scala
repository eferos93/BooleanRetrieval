package org.information_retrieval.boolean_retrieval

import scala.util.{Failure, Success, Try}

trait PostingTrait[T](documentId: T) {
  def get_from_corpus(corpus: LazyList[Movie]): Option[Movie]
}

case class Posting(documentId: Int) extends Ordered[Posting], PostingTrait[Int](documentId) {
  def get_from_corpus(corpus: LazyList[Movie]): Option[Movie] = Try(corpus(documentId)).toOption
  override def compare(that: Posting): Int = documentId compare that.documentId
  override def toString: String = documentId.toString
}
