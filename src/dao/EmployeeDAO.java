package dao;

import DB.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAO implements IDAO<Employee> {

    private static final String SELECT
            = "SELECT id, last_name, first_name, middle_name, position_id, salary, over_time_percent, department_id, subdivision_id, passport FROM employee ORDER BY id";
    private static final String SELECT_ONE
            = "SELECT id, last_name, first_name, middle_name, position_id, salary, over_time_percent," +
            " department_id, subdivision_id, passport FROM employee WHERE id=?";
    private static final String INSERT
            = "INSERT INTO employee (last_name, first_name, middle_name, position_id, salary," +
            " over_time_percent, department_id, subdivision_id, passport) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE
            = "UPDATE employee SET last_name=?, first_name=?, middle_name=?, position_id=?," +
            " salary=?, over_time_percent=?, department_id=?, subdivision_id=?, passport=? WHERE id=?";
    private static final String DELETE
            = "DELETE FROM employee WHERE id=?";

    private DBConnection con = new DBConnection();

    private Connection getConnection() throws SQLException {
        return con.getConnection();
    }

    @Override
    public void add(Employee employee) {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT, new String[]{"id"})) {
            Long id = -1L;
            pst.setString(1, employee.getLastName());
            pst.setString(2, employee.getFirstName());
            pst.setString(3, employee.getMiddleName());
            pst.setInt(4, employee.getPositionId());
            pst.setInt(5, employee.getSalary());
            pst.setInt(6, employee.getOverTimePercent());
            pst.setInt(7, employee.getDepartmenId());
            if (employee.getSubdivisionId() == 0) {
                pst.setObject(8, null);
            } else {
                pst.setInt(8, employee.getSubdivisionId());
            }
            pst.setInt(9, employee.getPassport());
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
    public void delete(Integer id) {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(DELETE)) {
            pst.setLong(1, id);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Employee employee) {
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(UPDATE)) {
            pst.setString(1, employee.getLastName());
            pst.setString(2, employee.getFirstName());
            pst.setString(3, employee.getMiddleName());
            pst.setInt(4, employee.getPositionId());
            pst.setInt(5, employee.getSalary());
            pst.setInt(6, employee.getOverTimePercent());
            pst.setInt(7, employee.getDepartmenId());
            if (employee.getSubdivisionId() == 0) {
                pst.setNull(8, 0);
            } else {
                pst.setInt(8, employee.getSubdivisionId());
            }
            pst.setInt(9, employee.getPassport());
            pst.setInt(10, employee.getId());
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Employee get(Integer id) {
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
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public ObservableList<Employee> find() {
        ObservableList<Employee> list = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(fillEmployee(rs));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private Employee fillEmployee(ResultSet rs) throws Exception {

        Employee employee = new Employee();
        PositionDAO positionDAO = new PositionDAO();
        DepartmentDAO departmentDAO = new DepartmentDAO();
        SubdivisionDAO subdivisionDAO = new SubdivisionDAO();
        CategoryDAO categoryDAO = new CategoryDAO();

        int positionId = rs.getInt("position_id");
        int departmentId = rs.getInt("department_id");
        int subdivisionId = rs.getInt("subdivision_id");

        int overPercent = rs.getInt("over_time_percent");
        int salary = rs.getInt("salary");

        employee.setId(rs.getInt("id"));
        employee.setLastName(rs.getString("last_name"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setMiddleName(rs.getString("middle_name"));
        employee.setOverTimePercent(overPercent);
        employee.setSalary(salary);
        employee.setPassport(rs.getInt("passport"));
        employee.setPosition(positionDAO.get(positionId));
        employee.setDepartment(departmentDAO.get(departmentId));
        employee.setSubdivision(subdivisionDAO.get(subdivisionId));

        Double badPercent = Double.parseDouble(employee.getDepartment().getCategory().getPercent().toString());
        Integer salaryT = (int) (salary * (1 + (badPercent / 100) + (overPercent / 100)));
        employee.setTotalSalary(salaryT);

        return employee;
    }
}
