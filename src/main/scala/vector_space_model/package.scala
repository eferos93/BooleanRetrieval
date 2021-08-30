package org.information_retrieval.boolean_retrieval

import scala.collection.SortedMap
import scala.io.Source
import scala.math.{log, sqrt, pow}

package object vector_space_model {
  type DocumentId = Int
  type TermPosition = Int

  //    replace everything that is not a letter or a space with empty string
  val normaliseText: String => String = _.replaceAll("[^a-zA-Z\\s]", "")

//  def importDataSet() = {
//    //    download it from: https://moodle2.units.it/mod/resource/view.php?id=195286
//    val dataSetPath = "time/TIME.ALL"
//    Source.fromFile(dataSetPath)
//      .getLines()
//      .withFilter(!_.isEmpty)
//      .mkString(System.lineSeparator())
//      .split("^\\*TEXT.?$")
//      .map(articleText => normaliseText(articleText).split(" "))
//  }

//TODO: make it functional style
  def importDataSet(): Array[(String, DocumentId, TermPosition)] = {
    var articles: Array[Array[String]] = Array.empty
    var temp: Array[String] = Array.empty
    Source.fromFile("time/TIME.ALL")
      .getLines()
      .withFilter(!_.isEmpty)
      .foreach { row =>
        row.startsWith("*TEXT") match {
          case true =>
            if !temp.isEmpty then
              articles = articles :+ temp
              temp = Array.empty
          case false =>
            temp = temp :++ normaliseText(row).split(" ").filterNot(_.isEmpty)
        }
      }
    articles.zipWithIndex
      .flatMap { article =>
        article._1.zipWithIndex.map { case (term, termPosition) => (term, article._2, termPosition)}
      }
  }

  def makePositionalIndex(articles: Array[(String, DocumentId, TermPosition)]) = {
    articles
      .groupMap(_._1){ case (term, documentId, termPosition) => (documentId, termPosition)}
      .map { term => (term._1 -> term._2.groupMap(_._1)(_._2).to(SortedMap))}
  }

  def documentAsVectors(articles: Array[(String, DocumentId, TermPosition)]) = {
    val index = makePositionalIndex(articles)
    val numberOfDocuments = articles.last._2 //they are sorted
    (0 to numberOfDocuments)
      .map { documentId =>
        index.map { term =>
          val termAppearance = term._2.size
          val tfidf = term._2.get(documentId) match {
            case Some(termPositions) => termPositions.length * log(numberOfDocuments/termAppearance)
            case None => 0
          }
          (term._1 -> tfidf)
        }
      }
  }

  def showDocumentVector(vector: IndexedSeq[Map[String, Double]], documentId: DocumentId) = {
    val sortedVector = vector(documentId).filter(_._2 > 0).toList.sortBy(- _._2) //reverse sorting
    val vectorLength = sqrt(sortedVector.map(termTfIdf => pow(termTfIdf._2, 2)).sum)
    sortedVector.map(termTfIdf => (termTfIdf._1 -> (termTfIdf._2, termTfIdf._2/vectorLength)))
      .foreach(termTfIdf => println(s"${termTfIdf._1}:\t${termTfIdf._2._1}\t(normalised tfidf: ${termTfIdf._2._2})"))
  }
}
