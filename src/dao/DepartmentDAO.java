package dao;

import DB.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Category;
import model.Department;

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


    // Удаление отдела по ее ID
    public void deleteDepartment(Integer id) throws Exception {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(DELETE)) {
            pst.setLong(1, id);
            pst.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


    // Редактирование отдела
    public void updateDepartment(Department department) throws Exception {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE)) {
            pst.setLong(4, department.getId());
            pst.setString(1, department.getName());
            pst.setString(2, department.getType());
            pst.setInt(3, department.getCategoryId());
            pst.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


    // Получение отдела
    public Department getDepartment(Integer id) throws Exception {
        Department department = null;
        try (Connection con = getConnection()) {
            PreparedStatement pst = con.prepareStatement(SELECT_ONE);
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                department = fillDepartment(rs);
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            throw new Exception(e);
        }
        return department;
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
        int categoryId = rs.getInt("category_id");
        CategoryDAO cd = new CategoryDAO();
        Category category = cd.getCategory(categoryId);

        department.setId(rs.getInt("id"));
        department.setName(rs.getString("name"));
        department.setType(rs.getString("type"));
        department.setCategory(category);
        return department;
    }
}
