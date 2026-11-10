package murach.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import murach.business.Survey;

public class SurveyDB {

    public static synchronized void insert(String email, Survey survey) throws IOException {
        Path directory = Paths.get(System.getProperty("catalina.base",
                System.getProperty("user.home")), "data");
        Files.createDirectories(directory);

        String row = clean(email) + "\t"
                + clean(survey.getHearAboutUs()) + "\t"
                + clean(survey.getContactMethod()) + "\t"
                + survey.isReceiveAnnouncements() + System.lineSeparator();

        Files.write(directory.resolve("surveys.tsv"),
                row.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public static void insert(Connection connection, String email, Survey survey) throws SQLException {
        String sql = "INSERT INTO surveys (user_email, hear_about_us, contact_method, receive_announcements) "
                + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, survey.getHearAboutUs());
            statement.setString(3, survey.getContactMethod());
            statement.setBoolean(4, survey.isReceiveAnnouncements());
            statement.executeUpdate();
        }
    }

    private static String clean(String value) {
        return value == null ? "" : value.replace('\t', ' ')
                .replace('\r', ' ').replace('\n', ' ');
    }
}
