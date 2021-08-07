name := "BooleanRetrieval"

version := "0.1"

scalaVersion := "3.0.1"

idePackagePrefix := Some("org.information_retrieval.boolean_retrieval")

//lazy val downloadFromZip = taskKey[Unit]("Download data and extract it")
//downloadFromZip := {
//  if (java.nio.file.Files.notExists(new File("data").toPath)) {
//    println("path /data/ does not exist, creating..")
//    IO.unzipURL(new URL("http://www.cs.cmu.edu/~ark/personas/data/MovieSummaries.tar.gz"), new File("data"))
//  } else {
//    println("path already exists, no need to download")
//  }
//}
//
////TODO: usage of in is deprecated
//compile in Compile <<= (compile in Compile).dependsOn(downloadFromZip)