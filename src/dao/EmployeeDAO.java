package dao;

import DB.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Employee;
import model.Position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAO {

    private static final String SELECT
            = "SELECT id, last_name, first_name, middle_name, position_id, salary, over_time_percent, department_id, subdivision_id FROM employee";
    private static final String SELECT_ONE
            = "SELECT id, last_name, first_name, middle_name, position_id, salary, over_time_percent," +
            " department_id, subdivision_id FROM employee WHERE id=?";
    private static final String INSERT
            = "INSERT INTO employee (last_name, first_name, middle_name, position_id, salary," +
            " over_time_percent, department_id, subdivision_id) VALUES (?,?,?,?,?,?,?,?)";
    private static final String UPDATE
            = "UPDATE employee SET last_name=?, first_name=?, middle_name=?, position_id=?," +
            " salary=?, over_time_percent=?, department_id=?, subdivision_id=? WHERE id=?";
    private static final String DELETE
            = "DELETE FROM employee WHERE id=?";

    private DBConnection con = new DBConnection();

    private Connection getConnection() throws SQLException {
        return con.getConnection();
    }


    // Получение работника
    public Employee getEmployee(Long id) throws Exception {
        Employee employee = null;
        try (Connection con = getConnection()) {
            PreparedStatement pst = con.prepareStatement(SELECT_ONE);
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                employee = fillEmployee(rs);
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            throw new Exception(e);
        }
        return employee;
    }

    // Получение списка работников
    public ObservableList<Employee> findEmployee() throws Exception {
        ObservableList<Employee> list = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(fillEmployee(rs));
            }
            rs.close();
        } catch (Exception e) {
            throw new Exception(e);
        }
        return list;
    }

    private Employee fillEmployee(ResultSet rs) throws Exception {

        Employee employee = new Employee();
        PositionDAO positionDAO = new PositionDAO();
        DepartmentDAO departmentDAO = new DepartmentDAO();
        SubdivisionDAO subdivisionDAO = new SubdivisionDAO();

        int positionId = rs.getInt("position_id");
        int departmentId = rs.getInt("department_id");
        int subdivisionId = rs.getInt("subdivision_id");

        employee.setId(rs.getLong("id"));
        employee.setLastName(rs.getString("last_name"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setMiddleName(rs.getString("middle_name"));
        employee.setOverTimePercent(rs.getInt("over_time_percent"));
        employee.setSalary(rs.getInt("salary"));
        employee.setPosition(positionDAO.getPosition((long) positionId).getName());
        employee.setDepartment(departmentDAO.getDepartment((long) departmentId).getName());
        employee.setSubdivision(subdivisionDAO.getSubdivision((long) subdivisionId).getName());

        return employee;
    }
}
