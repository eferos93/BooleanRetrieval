package org.information_retrieval.boolean_retrieval

import java.io.{File, FileInputStream, FileOutputStream}
import java.util.zip.GZIPInputStream

object Utils {
//  def downloadData(folder: String, url: String): Unit = {
//    import scala.language.postfixOps
//    import java.net.URL
//    import sys.process._
//    new URL(url) #> new File(folder) !!
//  }
//
//  val dataFolder: File = File("/data/")
//  val url: String = "http://www.cs.cmu.edu/~ark/personas/data/MovieSummaries.tar.gz"
//  println(java.nio.file.Files.notExists(new File("data").toPath))
//  if (java.nio.file.Files.notExists(new File("data").toPath)) {
//    println("data not found, downloading...")
//    downloadData("data.tar.gz", url)
//    val gzipInputStream: GZIPInputStream = new GZIPInputStream(new FileInputStream(new File("data.tar.gz")))
//    val buffer: Array[Byte] = new Array[Byte](1024)
//    val fileOutputStream: FileOutputStream = new FileOutputStream("data")
//    var length = 0;
//    while (length = gzipInputStream.read(buffer)) > 0 do
//      length
//      fileOutputStream.write(buffer, 0, )

    //    import java.io.{FileInputStream, FileOutputStream}
    //    import java.util.zip.ZipInputStream
    //    println("Data path not found, downloading...")
    //    downloadData("data.tar.gz", url)
    //    val fileInputStream = new FileInputStream("data.tar.gz")
    //    val gZipInputStream = new GZIPInputStream(fileInputStream)
    //    Stream.continually(gZipInputStream).takeWhile(_ != null).foreach {
    //      file =>
    //        val fileOutputStream = new FileOutputStream(file.getName)
    //        val buffer = new Array[Byte](1024)
    //        Stream.continually(gZipInputStream.read(buffer))
    //          .takeWhile(_ != -1)
    //          .foreach(fileOutputStream.write(buffer, 0, _))
    //    }

//  }
  //  dataFolder.length() match {
  //    case 0 => downloadData(dataFolder.getPath, url)
  //  }
}
