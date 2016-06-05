import sbt.Keys._
import sbt._

object Versions {

  val akka = "2.3.12"
  val scalaTestVer = "2.2.2"
  val mockito = "1.10.8"
  val postgresqlVersion = "9.4-1201-jdbc41"
  val slickVersion = "3.1.1"
  val scalaVersion = "2.11.7"
  val specs2Version = "2.4.6"
}

object Library {

  import Versions._

  // Core dependencies
  val akkaKit = "com.typesafe.akka" %% "akka-actor" % akka
  val slick = "com.typesafe.slick" %% "slick" % slickVersion
  val scalaLang = "org.scala-lang" % "scala-reflect" % scalaVersion
  val postgresql = "org.postgresql" % "postgresql" % postgresqlVersion
  val hikariCP = "com.zaxxer" % "HikariCP" % "2.4.3"

  val apacheCommons = "org.apache.commons" % "commons-email" % "1.3.3"

  val slickHikari = "com.typesafe.slick" %% "slick-hikaricp" % slickVersion
  val codegen = "com.typesafe.slick" %% "slick-codegen" % slickVersion
  val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.1.2"

  val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVer

  val specs2TestKit = "org.specs2" %% "specs2" % specs2Version

}

object Dependencies {

  import Library._

  val coreDeps = Seq(akkaKit, logbackClassic)

  val slickDeps = Seq(slick, hikariCP, slickHikari,postgresql, codegen)
  val testLibraryDependencies = Seq(scalaTest, specs2TestKit)
}


object CustomBuild extends Build {

  import Dependencies._

  val common_settings = Defaults.coreDefaultSettings ++
    Seq(
      organization := "com.yadu",
      scalaVersion in ThisBuild := "2.11.7",
      scalacOptions ++= Seq("-unchecked", "-feature", "-deprecation"),
      ivyScala := ivyScala.value map {
        _.copy(overrideScalaVersion = true)
      },


      libraryDependencies := coreDeps ++ slickDeps ++ testLibraryDependencies
    )

  lazy val slick_3_samples: Project = Project(id = "slick-3-samples",
    base = file("."), settings = common_settings)

}