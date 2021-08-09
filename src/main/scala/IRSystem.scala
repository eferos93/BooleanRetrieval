package org.information_retrieval.boolean_retrieval

class IRSystem(corpus: LazyList[Movie], invertedIndex: InvertedIndex) {
  def answerQuery(words: Array[String]): Array[Movie] = {
    words.map(invertedIndex.get compose normaliseText)
      .withFilter(_.isDefined)
      .map(_.get.postingList)
      .reduce((firstPostingList, secondPostingList) => firstPostingList.intersection(secondPostingList))
      .getFromCorpus(corpus)
  }
}

object IRSystem {
  def apply(corpus: LazyList[Movie]): IRSystem = {
    val invertedIndex: InvertedIndex = InvertedIndex(corpus)
    new IRSystem(corpus, invertedIndex)
  }
}
