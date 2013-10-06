import sbt._
import sbt.Keys._

object TypesafeConfigExamplesBuild extends Build {

  lazy val typesafeConfigExamples = Project(
    id = "typesafe-config-examples",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "Typesafe Config Examples",
      organization := "pl.mkubala",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.2",

      libraryDependencies := Seq(
        "org.scala-lang" % "scala-reflect" % "2.10.2",
        "com.typesafe" % "config" % "1.0.2"
      )

    )
  )
}
