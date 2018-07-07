scalaVersion := "2.11.6"

name := "programmers-den"
organization := "ch.epfl.scala"
version := "1.0"


import com.typesafe.sbt.packager.archetypes.JavaAppPackaging
enablePlugins(JavaAppPackaging)

libraryDependencies += "postgresql" % "postgresql" % "9.1-901.jdbc4"

libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-slick" % "0.19"

libraryDependencies += "com.github.tminglei" %% "slick-pg_joda-time" % "0.16.2"

libraryDependencies += "com.github.tminglei" %% "slick-pg_jts" % "0.16.2"

libraryDependencies += "com.github.tminglei" %% "slick-pg_json4s" % "0.16.2"

libraryDependencies += "com.github.tminglei" %% "slick-pg_spray-json" % "0.16.2"

libraryDependencies ++=Seq (
    "commons-io" % "commons-io" % "2.1",
	"org.flywaydb"		  % "flyway-core" 		   % "3.2.1",
	"org.scalatest"      %% "scalatest"            % "3.0.5" % "test",
	"ch.megard" %% "akka-http-cors" % "0.2.1",
	"com.typesafe.akka"  %% "akka-http"            % "10.0.8",
	"com.typesafe.akka"  %% "akka-http-spray-json" % "10.0.8",
    "com.typesafe.akka"  %% "akka-stream"          % "2.5.3",
	"com.typesafe.slick" %% "slick-hikaricp"	   % "3.2.0",
	"com.typesafe.akka"  %% "akka-actor"           % "2.5.3",
	"com.github.tminglei" %% "slick-pg" % "0.16.2"
     )
 resolvers += "mvnrepository" at "http://mvnrepository.com/artifact/"
 resolvers += "central" at "https://repo1.maven.org/maven2/" 

enablePlugins(FlywayPlugin)
name := "plugtest"
version := "0.0.1"
name := "flyway-sbt-test1"

libraryDependencies += "org.hsqldb" % "hsqldb" % "2.2.8"

flywayUrl := "jdbc:postgresql://localhost/"
flywayUser := "postgres"
flywayPassword := "postgres"
flywayLocations += "filesystem:src/main/Resources/db/migration"
flywayUrl in Test := "jdbc:postgresql://localhost/"
flywayUser in Test := "postgres"
flywayPassword := "postgres"
