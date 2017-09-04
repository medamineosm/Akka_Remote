name := "ScrapperRemote"

version := "1.0"

scalaVersion := "2.11.3"

// https://mvnrepository.com/artifact/com.typesafe.akka/akka-actor_2.12
libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.4.16"
libraryDependencies += "com.typesafe.akka" % "akka-remote_2.11" % "2.4.16"
libraryDependencies += "org.jsoup" % "jsoup" % "1.8.3"
libraryDependencies += "org.mongodb" %% "casbah" % "3.1.1"
libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.6.4"