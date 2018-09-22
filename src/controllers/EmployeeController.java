package controllers;

import dao.EmployeeDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Employee;

public class EmployeeController {

    private ObservableList<Employee> employeesData = FXCollections.observableArrayList();

    @FXML
    private TableView<Employee> tableEmployee;
    @FXML
    private TableColumn<Employee, String> last_name;
    @FXML
    private TableColumn<Employee, String> first_name;
    @FXML
    private TableColumn<Employee, String> middle_name;
    @FXML
    private TableColumn<Employee, String> position;
    @FXML
    private TableColumn<Employee, Integer> salary;
    @FXML
    private TableColumn<Employee, Integer> percent;
    @FXML
    private TableColumn<Employee, String> department;
    @FXML
    private TableColumn<Employee, String> subdivision;

    @FXML
    private Button update;

    private EmployeeDAO employeeDAO = new EmployeeDAO();

    @FXML
    private void initialize() throws Exception {
        initData();
        last_name.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        first_name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        middle_name.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        position.setCellValueFactory(new PropertyValueFactory<>("position"));
        department.setCellValueFactory(new PropertyValueFactory<>("department"));
        percent.setCellValueFactory(new PropertyValueFactory<>("overTimePercent"));
        subdivision.setCellValueFactory(new PropertyValueFactory<>("subdivision"));

        update.setOnAction(event -> {
            try {
                employeesData.clear();
                employeesData.addAll(employeeDAO.findEmployee());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        tableEmployee.setItems(employeesData);
    }

    private void initData() throws Exception {
        employeesData.addAll(employeeDAO.findEmployee());
    }
}
