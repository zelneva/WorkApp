package dao;

import DB.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Subdivision;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubdivisionDAO {

    private static final String SELECT
            = "SELECT id, name, department_id FROM subdivision ORDER BY name";
    private static final String SELECT_ONE
            = "SELECT id, name, department_id FROM subdivision WHERE id=?";
    private static final String INSERT
            = "INSERT INTO subdivision (name, department_id) VALUES (?, ?)";
    private static final String UPDATE
            = "UPDATE subdivision SET name=?, department_id=? WHERE id=?";
    private static final String DELETE
            = "DELETE FROM subdivision WHERE id=?";

    private DBConnection con = new DBConnection();

    private Connection getConnection() throws SQLException {
        return con.getConnection();
    }


    // Получение отдела
    public Subdivision getSubdivision(Long id) throws Exception {
        Subdivision subdivision = null;
        try (Connection con = getConnection()) {
            PreparedStatement pst = con.prepareStatement(SELECT_ONE);
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                subdivision = fillSubdivision(rs);
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            throw new Exception(e);
        }
        return subdivision;
    }

    // Получение списка подразделений
    public ObservableList<Subdivision> findSubdivision() throws Exception {
        ObservableList<Subdivision> list = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(fillSubdivision(rs));
            }
            rs.close();
        } catch (Exception e) {
            throw new Exception(e);
        }
        return list;
    }

    private Subdivision fillSubdivision(ResultSet rs) throws Exception {
        Subdivision subdivision = new Subdivision();
        int departmentId = rs.getInt("department_id");
        DepartmentDAO dd = new DepartmentDAO();

        subdivision.setId(rs.getLong("id"));
        subdivision.setName(rs.getString("name"));
        subdivision.setDepartment(dd.getDepartment((long) departmentId).getName());
        return subdivision;
    }

}
