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


//    private void insert(Connection con,  String str, Integer per) throws SQLException {
//        PreparedStatement stmt = con.prepareStatement("INSERT INTO CATEGORY ( NAME, PERCENT) VALUES ( ?, ?)");
//        stmt.setString(1, str);
//        stmt.setInt(2, per);
//        stmt.executeUpdate();
//        stmt.close();
//    }
//
//    private void select(Connection con) throws SQLException {
//        Statement stmt = con.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT * FROM CATEGORY");
//        while (rs.next()) {
//            String str = rs.getString("name") + rs.getString(3)+ "%";
//            System.out.println("Name:" + str);
//        }
//        rs.close();
//        stmt.close();
//    }

//}
