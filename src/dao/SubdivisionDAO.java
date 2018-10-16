package dao;

import DB.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Position;
import model.Subdivision;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubdivisionDAO implements  IDAO<Subdivision>{

    private static final String SELECT
            = "SELECT id, name, department_id FROM subdivision ORDER BY id";
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

    @Override
    public void add(Subdivision subdivision) {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT, new String[]{"id"})) {
            Long id = -1L;
            pst.setString(1, subdivision.getName());
            pst.setInt(2, subdivision.getDepartmentId());
            pst.executeUpdate();
            ResultSet gk = pst.getGeneratedKeys();
            if (gk.next()) {
                id = gk.getLong("id");
            }
            gk.close();

        } catch (Exception e) {
            e.getMessage();
        }
    }


    @Override
    public void update(Subdivision subdivision) {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE)) {
            pst.setLong(3, subdivision.getId());
            pst.setString(1, subdivision.getName());
            pst.setInt(2, subdivision.getDepartmentId());
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(Integer id) {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(DELETE)) {
            pst.setLong(1, id);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Получение отдела
    @Override
    public Subdivision get(Integer id){
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
           e.printStackTrace();
        }
        return subdivision;
    }

    // Получение списка подразделений
    @Override
    public ObservableList<Subdivision> find() {
        ObservableList<Subdivision> list = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(fillSubdivision(rs));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private Subdivision fillSubdivision(ResultSet rs) throws Exception {
        Subdivision subdivision = new Subdivision();
        int departmentId = rs.getInt("department_id");
        DepartmentDAO dd = new DepartmentDAO();

        subdivision.setId(rs.getInt("id"));
        subdivision.setName(rs.getString("name"));
        subdivision.setDepartment(dd.get(departmentId));
        return subdivision;
    }

}
