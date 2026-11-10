package murach.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
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
                + clean(user.getEmail()) + "\t"
                + clean(user.getBirthDate()) + System.lineSeparator();

        Files.write(directory.resolve("users.tsv"),
                row.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public static void insert(Connection connection, User user) throws SQLException {
        String sql = "INSERT INTO users (email, first_name, last_name, birth_date) VALUES (?, ?, ?, ?) "
                + "ON CONFLICT (email) DO UPDATE SET first_name = EXCLUDED.first_name, "
                + "last_name = EXCLUDED.last_name, birth_date = EXCLUDED.birth_date";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setObject(4, LocalDate.parse(user.getBirthDate()));
            statement.executeUpdate();
        }
    }

    private static String clean(String value) {
        return value == null ? "" : value.replace('\t', ' ')
                .replace('\r', ' ').replace('\n', ' ');
    }
}
