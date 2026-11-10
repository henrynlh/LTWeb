package murach.data;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class Database {
    private Database() {}

    public static boolean isConfigured() {
        String url = System.getenv("DATABASE_URL");
        return url != null && !url.isBlank();
    }

    public static Connection getConnection() throws SQLException {
        String url = System.getenv("DATABASE_URL");
        if (!isConfigured()) {
            throw new SQLException("DATABASE_URL is not configured.");
        }
        try {
            Class.forName("org.postgresql.Driver");
            URI uri = new URI(url);
            if (!("postgresql".equals(uri.getScheme()) || "postgres".equals(uri.getScheme()))
                    || uri.getHost() == null || uri.getRawUserInfo() == null
                    || uri.getRawPath() == null || uri.getRawPath().length() < 2) {
                throw new SQLException("DATABASE_URL must be a PostgreSQL connection URL from Render.");
            }
            String[] credentials = uri.getRawUserInfo().split(":", 2);
            if (credentials.length != 2) {
                throw new SQLException("DATABASE_URL must include a username and password.");
            }
            Properties properties = new Properties();
            properties.setProperty("user", decode(credentials[0]));
            properties.setProperty("password", decode(credentials[1]));
            properties.setProperty("sslmode", "require");
            properties.setProperty("connectTimeout", "10");
            properties.setProperty("socketTimeout", "30");
            int port = uri.getPort() == -1 ? 5432 : uri.getPort();
            // Keep credentials separate from the JDBC URL.
            String jdbcUrl = "jdbc:postgresql://" + uri.getHost() + ":" + port + uri.getRawPath();
            return DriverManager.getConnection(jdbcUrl, properties);
        } catch (URISyntaxException | IllegalArgumentException | ClassNotFoundException ex) {
            throw new SQLException("Cannot configure PostgreSQL connection. Check DATABASE_URL and JDBC driver.", ex);
        }
    }

    private static String decode(String value) {
        return URLDecoder.decode(value.replace("+", "%2B"), StandardCharsets.UTF_8);
    }
}
