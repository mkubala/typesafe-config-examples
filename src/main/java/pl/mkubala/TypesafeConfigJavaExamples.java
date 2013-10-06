package pl.mkubala;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import static java.lang.System.out;

public class TypesafeConfigJavaExamples {

    private final Config config = ConfigFactory.load();

    public static void main(final String[] args) {
        TypesafeConfigJavaExamples examples = new TypesafeConfigJavaExamples();

        examples.simpleReading();
    }

    private void simpleReading() {
        out.println(config.getString("my.organization.project.name"));
        out.println(config.getString("my.organization.project.description"));
        out.println(config.getInt("my.organization.team.avgAge"));
        out.println(config.getStringList("my.organization.team.members"));
    }


}
