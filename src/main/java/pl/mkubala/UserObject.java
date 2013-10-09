package pl.mkubala;

import com.typesafe.config.Config;

public class UserObject {

    private final String name;

    private final int age;

    public UserObject(final Config params) {
        name = params.getString("name");
        age = params.getInt("age");
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserObject that = (UserObject) o;

        if (age != that.age) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + age;
        return result;
    }

    @Override
    public String toString() {
        return "UserObject{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
