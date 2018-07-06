scalaVersion := "2.11.6"

name := "programmers-den"
organization := "ch.epfl.scala"
version := "1.0"


import com.typesafe.sbt.packager.archetypes.JavaAppPackaging
enablePlugins(JavaAppPackaging)

libraryDependencies += "postgresql" % "postgresql" % "9.1-901.jdbc4"

libraryDependencies += "com.typesafe.slick" %% "slick" % "3.2.3"

libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-slick" % "0.19"

libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.3"

libraryDependencies ++=Seq (
    "commons-io" % "commons-io" % "2.1",
	"org.flywaydb"		  % "flyway-core" 		   % "3.2.1",
	"org.scalatest"      %% "scalatest"            % "3.0.5" % "test",
	"ch.megard" %% "akka-http-cors" % "0.2.1",
	"com.typesafe.akka"  %% "akka-http"            % "10.0.8",
	"com.typesafe.akka"  %% "akka-http-spray-json" % "10.0.8",
    "com.typesafe.akka"  %% "akka-stream"          % "2.5.3",
	"com.typesafe.slick" %% "slick-hikaricp"	   % "3.2.0",
	"com.typesafe.akka"  %% "akka-actor"           % "2.5.3"
     )
 resolvers += "mvnrepository" at "http://mvnrepository.com/artifact/"
 resolvers += "central" at "https://repo1.maven.org/maven2/" 
enablePlugins(FlywayPlugin)
    
flywayUrl := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser := "SA"
flywayLocations += "src/main/Resources/db/migration"
flywaySchemas := Seq("schema1", "schema2", "schema3")
flywayPlaceholders := Map(
    "keyABC" -> "valueXYZ",
    "otherplaceholder" -> "value123"
)
