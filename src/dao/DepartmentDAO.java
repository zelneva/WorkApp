package dao;

import DB.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Category;
import model.Department;
import org.postgresql.core.Parser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentDAO {

    private static final String SELECT
            = "SELECT id, name, type, category_id FROM department ORDER BY name";
    private static final String SELECT_ONE
            = "SELECT id, name, type, category_id FROM department WHERE id=?";
    private static final String INSERT
            = "INSERT INTO department (name, type, category_id) VALUES (?, ?, ?)";
    private static final String UPDATE
            = "UPDATE department SET name=?, type=?, category_id=? WHERE id=?";
    private static final String DELETE
            = "DELETE FROM department WHERE id=?";

    private DBConnection con = new DBConnection();

    private Connection getConnection() throws SQLException {
        return con.getConnection();
    }

    // Получение списка отделов
    public ObservableList<Department> findDepartment() throws Exception {
        ObservableList<Department> list = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(fillDepartment(rs));
            }
            rs.close();
        } catch (Exception e) {
            throw new Exception(e);
        }
        return list;
    }

    private Department fillDepartment(ResultSet rs) throws Exception {
        Department department = new Department();
        int a = rs.getInt("category_id");
        Category category = new Category();
        CategoryDAO cd = new CategoryDAO();

        department.setId(rs.getLong("id"));
        department.setName(rs.getString("name"));
        department.setType(rs.getString("type"));
        department.setCategory(cd.getCategory(Long.valueOf(a)).getName());
        return department;
    }

}
