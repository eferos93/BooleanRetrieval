package org.information_retrieval.boolean_retrieval

import scala.io.Source

case class Movie(title: String, description: String) {
  override def toString: String = title
}