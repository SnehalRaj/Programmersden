
scalaVersion := "2.12.4"

name := "hello-world"
organization := "ch.epfl.scala"
version := "1.0"


import com.typesafe.sbt.packager.archetypes.JavaAppPackaging
enablePlugins(JavaAppPackaging)

libraryDependencies += "postgresql" % "postgresql" % "9.1-901.jdbc4"

libraryDependencies += "com.typesafe.slick" %% "slick" % "2.0.0-M1"

libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-slick" % "0.19"

libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.6.4"