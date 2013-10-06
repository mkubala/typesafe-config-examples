package pl.mkubala

import com.typesafe.config.ConfigFactory

object TypesafeConfigScalaExamples {

  val config = ConfigFactory.load()

  def main(args: Array[String]) {
    basicReading()
  }

  def basicReading() {
    println(config.getString("my.organization.project.name"))
    println(config.getString("my.organization.project.description"))
    println(config.getInt("my.organization.team.avgAge"))
    println(config.getStringList("my.organization.team.members"))
  }

}
