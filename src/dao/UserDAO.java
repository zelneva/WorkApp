package model;

import DB.DBConnection;
import dao.IDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements IDAO<User> {

    private static final String SELECT
            = "SELECT id, name, password, role FROM users ORDER BY id";
    private static final String SELECT_ONE
            = "SELECT id, name, password, role FROM users WHERE id=?";
    private static final String INSERT
            = "INSERT INTO users (name, password, role) VALUES (?,?,?)";
    private static final String UPDATE
            = "UPDATE users SET name=?, password = ?, role=? WHERE id=?";
    private static final String DELETE
            = "DELETE FROM users WHERE id=?";

    private DBConnection con = new DBConnection();

    private Connection getConnection() throws SQLException {
        return con.getConnection();
    }

    @Override
    public void add(User user) {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT, new String[]{"id"})) {
            Long id = -1L;
            pst.setString(1, user.getName());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getRole());
            pst.executeUpdate();
            ResultSet gk = pst.getGeneratedKeys();
            if (gk.next()) {
                id = gk.getLong("id");
            }
            gk.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public User get(Integer id) {
        User user = null;
        try (Connection con = getConnection()) {
            PreparedStatement pst = con.prepareStatement(SELECT_ONE);
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                user = fillUser(rs);
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    private User fillUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        return user;
    }

    @Override
    public ObservableList<User> find() {
        ObservableList<User> list = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(fillUser(rs));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
