package pl.mkubala

import com.typesafe.config.ConfigFactory

object TypesafeConfigScalaExamples {

  def main(args: Array[String]) {
    val config = ConfigFactory.load()

    println(config.getString("my.organization.project.name"))
    println(config.getString("my.organization.project.description"))
    println(config.getInt("my.organization.team.avgAge"))
  }

}
