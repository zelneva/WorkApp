package controllers;

import dao.EmployeeDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Department;
import model.Employee;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class EmployeeController extends IController<Employee> {

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
    private TableColumn<Employee, Integer> passport;
    @FXML
    private TableColumn<Employee, Integer> total_salary;

    @FXML
    private TextField searchFieldComponent;

    @FXML
    private Button add;


    private EmployeeDAO employeeDAO = new EmployeeDAO();

    @FXML
    private void initialize() {

        if (tableEmployee == null) return;

        last_name.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        first_name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        middle_name.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        position.setCellValueFactory(new PropertyValueFactory<>("positionName"));
        department.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        percent.setCellValueFactory(new PropertyValueFactory<>("overTimePercent"));
        subdivision.setCellValueFactory(new PropertyValueFactory<>("subdivisionName"));
        passport.setCellValueFactory(new PropertyValueFactory<>("passport"));
        total_salary.setCellValueFactory(new PropertyValueFactory<>("totalSalary"));

        initData(employeesData, tableEmployee, employeeDAO, searchFieldComponent);

        add.setOnAction(event -> {
            try {
                EmployeeFormController.addEmployee()
                        .setOnChangeListener(() -> update(employeesData, employeeDAO, tableEmployee));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        searchFieldComponent.textProperty().addListener((observable, oldValue, newValue) -> {
            applyFilter();
        });
    }


    @FXML
    private void applyFilter() {
        String searchWord = searchFieldComponent.getText().toLowerCase().trim();
        ObservableList<Employee> filterList = this.employeesData
                .stream().filter(i -> i.getLastName().toLowerCase().trim().contains(searchWord))
                .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
        setList(filterList, tableEmployee);
    }


    public void deleteEmployee(ActionEvent event) {
        int index = tableEmployee.getSelectionModel().getFocusedIndex();
        Integer id = tableEmployee.getItems().get(index).getId();
        delete(id, tableEmployee, employeeDAO, employeesData);
    }


    public void editEmployee(ActionEvent event) throws Exception {
        int index = tableEmployee.getSelectionModel().getFocusedIndex();
        Employee employee = tableEmployee.getItems().get(index);
        EmployeeFormController.editEmployee(employee)
                .setOnChangeListener(() -> update(employeesData, employeeDAO, tableEmployee));

    }
}
