package pl.mkubala

import com.typesafe.config._

object TypesafeConfigScalaExamples {

  val config = ConfigFactory.load()

  def display(codeAsString: String)(body: => Any) {
    println(s"$codeAsString \n\t\t\t=> $body\n")
  }

  def main(args: Array[String]) {
    basicReading()
    extractNestedConfig()
    listingPathsAndKeys()
    valuesOverriding()
    creatingAndRendering()
  }

  private def basicReading() {
    display("config.getString(\"my.organization.project.name\")") {
      config.getString("my.organization.project.name")
    }

    // my.organization.project.description contains substitution
    display("config.getString(\"my.organization.project.description\")") {
      config.getString("my.organization.project.description")
    }

    display("config.getInt(\"my.organization.team.avgAge\")") {
      config.getInt("my.organization.team.avgAge")
    }

    display("config.getStringList(\"my.organization.team.members\")") {
      config.getStringList("my.organization.team.members")
    }
  }

  private def extractNestedConfig() {
    val extractedConfig: Config = config.getConfig("my.organization.team");

    display("extractedConfig.getInt(\"avgAge\")") {
      extractedConfig.getInt("avgAge")
    }

    display("extractedConfig.getStringList(\"members\")") {
      extractedConfig.getStringList("members")
    }
  }

  private def listingPathsAndKeys() {
    import scala.collection.JavaConversions._

    // List paths
    display("config.entrySet() map (_.getKey)") {
      config.entrySet() map (_.getKey)
    }

    // List root's direct children keys
    display("config.root().keySet()") {
      config.root().keySet()
    }

    // List only my.organization sub-paths
    display("config.withOnlyPath(\"my.organization\").entrySet() map (_.getKey)") {
      config.withOnlyPath("my.organization").entrySet() map (_.getKey)
    }

  }

  private def valuesOverriding() {
    // Displays original value
    display("config.getString(\"my.organization.project.name\")") {
      config.getString("my.organization.project.name")
    }

    // Config is immutable
    val updatedConfig: Config = config.withValue("my.organization.project.name", ConfigValueFactory.fromAnyRef("RebrandedProject"))
    display("updatedConfig = config.withValue(\"my.organization.project.name\", ConfigValueFactory.fromAnyRef(\"RebrandedProject\")") {
      "[OK]"
    }

    // Displays updated value
    display("updatedConfig.getString(\"my.organization.project.name\")") {
      updatedConfig.getString("my.organization.project.name")
    }

    // Currently I have no idea how to 'reload' substitutions so this one
    // displays description with old project's name :(
    display("updatedConfig.getString(\"my.organization.project.description\")") {
      updatedConfig.getString("my.organization.project.description")
    }

    // Displays original value one more time - to prove the immutability
    display("config.getString(\"my.organization.project.name\")") {
      config.getString("my.organization.project.name")
    }
  }

  private def creatingAndRendering() {
    renderConfig(createDinnerConfig())
  }

  private def createDinnerConfig(): Config = {
    import scala.collection.JavaConversions._
    ConfigFactory.empty()
      .withValue("dish.name", ConfigValueFactory.fromAnyRef("SomeCompany"))
      .withValue("dish.estimatedCost", ConfigValueFactory.fromAnyRef(10))
      .withValue("dish.ingredients", ConfigValueFactory.fromIterable(
      List(
        "potato", "bacon", "onion", "salt", "pepper"
      )))
  }

  private def renderConfig(config: Config) {
    // See ConfigRenderOptions' JavaDoc for details.
    val renderOpts = ConfigRenderOptions.defaults().setOriginComments(false).setComments(false);
    //val renderOpts = ConfigRenderOptions.concise();

    display("config.root().render(renderOpts)") {
      config.root().render(renderOpts)
    }
  }

}
