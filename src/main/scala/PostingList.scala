package org.information_retrieval.boolean_retrieval

import scala.collection.mutable.SortedSet

class PostingList {
  var postings: SortedSet[Posting] = SortedSet.empty
  def merge(otherPostingList: PostingList): Unit = postings.concat(otherPostingList.postings)
  def intersection(otherPostingList: PostingList): PostingList = PostingList(postings.intersect(otherPostingList.postings))
  def union(otherPostingList: PostingList): PostingList = PostingList(postings.union(otherPostingList.postings))
  def getFromCorpus(corpus: LazyList[Movie]): List[Movie] = {
    postings.toList.collect {
      _.get_from_corpus(corpus) match
        case Some(movie) => movie
    }
  }
}

object PostingList {
  def apply(documentId: Int): PostingList = {
    var postingList = new PostingList
    postingList.postings = SortedSet(Posting(documentId))
    postingList
  }

  def apply(otherPostingList: SortedSet[Posting]): PostingList = {
    var postingList = new PostingList
    postingList.postings = otherPostingList
    postingList
  }
}
