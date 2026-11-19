package murach.data;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import murach.business.User;

public class UserDB {

    public static synchronized void insert(User user) throws IOException {
        Path directory = Paths.get(System.getProperty("catalina.base",
                System.getProperty("user.home")), "data");
        Files.createDirectories(directory);

        String row = clean(user.getFirstName()) + "\t"
                + clean(user.getLastName()) + "\t"
                + clean(user.getEmail()) + System.lineSeparator();

        Files.write(directory.resolve("users.tsv"),
                row.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    private static String clean(String value) {
        return value == null ? "" : value.replace('\t', ' ')
                .replace('\r', ' ').replace('\n', ' ');
    }
}
