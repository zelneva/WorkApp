package DB;

import java.sql.*;

public class DBConnection {

    public Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String login = "anastasia";
        String password = "postgres";
        return DriverManager.getConnection(url, login, password);
    }
}
