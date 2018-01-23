package pw.stamina.occasum;

import java.util.Locale;
import java.util.Objects;

public interface Named {

    String getName();

    String getId();

    //TODO Doc
    static String validateName(String name) {
        Objects.requireNonNull(name, "name is null");

        if (name.isEmpty()) {
            throw new IllegalArgumentException("name cannot be empty");
        } else if (name.contains(".")) {
            throw new IllegalArgumentException("name may not contain periods ('.')");
        }

        return name;
    }

    //TODO Doc
    static String createIdFromName(String name) {
        String id = name;

        id = id.toLowerCase(Locale.ROOT);
        id = id.replaceAll("(\\s|_)+", "_");

        return id;
    }
}
