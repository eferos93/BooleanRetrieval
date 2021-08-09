package org.information_retrieval.boolean_retrieval

class PostingList {
  var postings: Array[Posting] = Array.empty
  def merge(otherPostingList: PostingList): Unit = {
    val last = postings.last
    var i = 0
    while (i < otherPostingList.postings.size && last == otherPostingList.postings(i)) {
      i += 1
    }
    postings = postings.concat(otherPostingList.postings.slice(i, otherPostingList.postings.size))
  }
  def intersection(otherPostingList: PostingList): PostingList = PostingList(postings.intersect(otherPostingList.postings))
  def union(otherPostingList: PostingList): PostingList = PostingList(postings.concat(otherPostingList.postings).distinct)
  def getFromCorpus(corpus: LazyList[Movie]): Array[Movie] = {
    postings.collect {
      _.get_from_corpus(corpus) match
        case Some(movie) => movie
    }
  }

  override def toString: String = postings.mkString(", ")
}

object PostingList {
  def apply(documentId: Int): PostingList = {
    var postingList = new PostingList
    postingList.postings = Array(Posting(documentId))
    postingList
  }

  def apply(otherPostingList: Array[Posting]): PostingList = {
    var postingList = new PostingList
    postingList.postings = otherPostingList
    postingList
  }
}
