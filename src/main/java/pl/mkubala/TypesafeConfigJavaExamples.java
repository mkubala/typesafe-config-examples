package pl.mkubala;

import com.typesafe.config.*;

import java.util.*;

import static java.lang.System.out;

public class TypesafeConfigJavaExamples {

    private final Config config = ConfigFactory.load();

    private static void display(final String code, final Object results) {
        out.println(String.format("%s \n\t\t\t=> %s\n", code, results));
    }

    public static void main(final String[] args) {
        TypesafeConfigJavaExamples examples = new TypesafeConfigJavaExamples();

        examples.simpleReading();
        examples.extractNestedConfig();
        examples.nestedConfigUseCase();
        examples.fallbackExample();
        examples.listingPathsAndKeys();
        examples.valuesOverriding();
        examples.creatingAndRendering();
    }

    private void simpleReading() {
        display("config.getString(\"my.organization.project.name\")",
                config.getString("my.organization.project.name"));

        // my.organization.project.description contains substitution
        display("config.getString(\"my.organization.project.description\")",
                config.getString("my.organization.project.description"));

        display("config.getInt(\"my.organization.team.avgAge\")",
                config.getInt("my.organization.team.avgAge"));

        display("config.getStringList(\"my.organization.team.members\")",
                config.getStringList("my.organization.team.members"));
    }

    private void extractNestedConfig() {
        Config extractedConfig = config.getConfig("my.organization.team");

        display("extractedConfig.getInt(\"avgAge\")",
                extractedConfig.getInt("avgAge"));

        display("extractedConfig.getStringList(\"members\")",
                extractedConfig.getStringList("members"));
    }

    private void nestedConfigUseCase() {
        Config mainConfig = ConfigFactory.load("nestedConfExample");
        Collection<UserObject> users = new ArrayList<>();
        for (Config particularConfig : mainConfig.getConfigList("some.namespace.users")) {
            UserObject particularUser = new UserObject(particularConfig);
            users.add(particularUser);
        }
        display("users",
                users);
    }

    private void fallbackExample() {
        Config customs = ConfigFactory.load("custom").withFallback(config);

        display("customs.getString(\"my.organization.project.name\")",
                customs.getString("my.organization.project.name"));

        display("customs.getString(\"my.organization.team.avgAge\")",
                customs.getString("my.organization.team.avgAge"));
    }

    private void listingPathsAndKeys() {
        // List paths
        Set<String> keys = new HashSet<>();
        for (Map.Entry<String, ConfigValue> entry : config.entrySet()) {
            keys.add(entry.getKey());
        }
        display("config.entrySet() .. .getKey()",
                keys);

        // List root's direct children keys
        display("config.root().keySet()",
                config.root().keySet());

        // List only my.organization sub-paths
        Set<String> keys2 = new HashSet<>();
        for (Map.Entry<String, ConfigValue> entry : config.withOnlyPath("my.organization").entrySet()) {
            keys2.add(entry.getKey());
        }
        display("config.withOnlyPath(\"my.organization\").entrySet() .. .getKey()",
                keys2);

    }

    private void valuesOverriding() {
        // Displays original value
        display("config.getString(\"my.organization.project.name\")",
                config.getString("my.organization.project.name"));

        // Config is immutable
        Config updatedConfig = config.withValue("my.organization.project.name", ConfigValueFactory.fromAnyRef("RebrandedProject"));
        display("updatedConfig = config.withValue(\"my.organization.project.name\", ConfigValueFactory.fromAnyRef(\"RebrandedProject\")",
                "[OK]");

        // Displays updated value
        display("updatedConfig.getString(\"my.organization.project.name\")",
                updatedConfig.getString("my.organization.project.name"));

        // Currently I have no idea how to 'reload' substitutions so this one
        // displays description with old project's name :(
        display("updatedConfig.getString(\"my.organization.project.description\")",
                updatedConfig.getString("my.organization.project.description"));

        // Displays original value one more time - to prove the immutability
        display("config.getString(\"my.organization.project.name\")",
                config.getString("my.organization.project.name"));
    }

    private void creatingAndRendering() {
        renderConfig(createDinnerConfig());
    }

    private Config createDinnerConfig() {
        return ConfigFactory.empty()
                .withValue("dish.name", ConfigValueFactory.fromAnyRef("SomeCompany"))
                .withValue("dish.estimatedCost", ConfigValueFactory.fromAnyRef(10))
                .withValue("dish.ingredients", ConfigValueFactory.fromIterable(Arrays.asList(
                        "potato", "bacon", "onion", "salt", "pepper"
                )));
    }

    private void renderConfig(final Config config) {
        // See ConfigRenderOptions' JavaDoc for details.
        ConfigRenderOptions renderOpts = ConfigRenderOptions.defaults().setOriginComments(false).setComments(false);
        //ConfigRenderOptions renderOpts = ConfigRenderOptions.concise();

        display("config.root().render(renderOpts)",
                config.root().render(renderOpts));
    }

}
