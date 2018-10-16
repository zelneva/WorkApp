package controllers;

import dao.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

class EmployeeFormController extends FormController {

    @FXML
    TextField last_name;

    @FXML
    TextField first_name;

    @FXML
    TextField middle_name;

    @FXML
    ComboBox<Position> positionBox;

    @FXML
    TextField salary;

    @FXML
    TextField over_time_percent;

    @FXML
    ComboBox<Department> departmentBox;

    @FXML
    ComboBox<Subdivision> subdivisionBox;

    @FXML
    TextField passport;

    @FXML
    javafx.scene.control.Button saveBtn;

    private OnChangeListener listener;
    private Stage stage = new Stage();

    DepartmentDAO departmentDAO = new DepartmentDAO();
    SubdivisionDAO subdivisionDAO = new SubdivisionDAO();
    PositionDAO positionDAO = new PositionDAO();
    EmployeeDAO employeeDAO = new EmployeeDAO();

    Employee updateEmployee = new Employee();

    public EmployeeFormController(ActionType actionType) throws IOException {
        loadWindow("../view/employee_form.fxml", stage, this);
        addHeader(actionType, stage);

        ObservableList<Position> positionList = FXCollections.observableArrayList(positionDAO.find());
        positionBox.setItems(positionList);

        ObservableList<Department> departmentList = FXCollections.observableArrayList(departmentDAO.find());
        departmentBox.setItems(departmentList);

        departmentBox.valueProperty().addListener(new ChangeListener<Department>() {

            @Override
            public void changed(ObservableValue<? extends Department> observable, Department oldValue, Department newValue) {
                Integer id = departmentBox.getSelectionModel().getSelectedIndex();
                ObservableList<Subdivision> subdivisionList = FXCollections.observableArrayList(subdivisionDAO.find());
                ObservableList<Subdivision> subdivisionList2 = FXCollections.observableArrayList();
                for (Subdivision s : subdivisionList) {
                    if (s.getDepartmentId() == id + 1) {
                        subdivisionList2.add(s);
                    }
                }
                subdivisionList2.add(null);
                subdivisionBox.setItems(subdivisionList2);
            }
        });


        saveBtn.setOnAction(e -> {
            String lastName = last_name.getText();
            String firstName = first_name.getText();
            String middleName = middle_name.getText();
            Position position = positionBox.getValue();
            String salaryValue = salary.getText();
            String percent = over_time_percent.getText();
            Department department = departmentBox.getValue();
            Subdivision subdivision;
            if (subdivisionBox == null) {
                subdivision = new Subdivision();
            } else {
                subdivision = subdivisionBox.getValue();
            }
            String passportValue = passport.getText();


            if (!this.validate(lastName, firstName, middleName, position, salaryValue,
                    percent, department, subdivision, passportValue)) {
                showAlert();
                return;
            }

            if (actionType == ActionType.ADD) {
                if (unique(new Employee(lastName, firstName, middleName, position, Integer.getInteger(salaryValue),
                                Integer.getInteger(percent), department, subdivision, Integer.getInteger(passportValue)),
                        employeeDAO.find())) {
                    showAlertUnique();
                    return;
                }
                createEmployee(lastName, firstName, middleName, position, Integer.parseInt(salaryValue),
                        Integer.parseInt(percent), department, subdivision, Integer.parseInt(passportValue));
            }

            if (actionType == ActionType.EDIT) {
                editEmployee(lastName, firstName, middleName, position, Integer.parseInt(salaryValue),
                        Integer.parseInt(percent), department, subdivision, Integer.parseInt(passportValue));
            }

            if (listener != null) {
                listener.onChange();
            }

            stage.close();
        });

    }


    private void createEmployee(String lastName, String firstName, String middleName,
                                Position position, Integer salary, Integer percent,
                                Department department, Subdivision subdivision,
                                Integer passport) {
        Employee employee = new Employee(lastName, firstName, middleName, position, salary,
                percent, department, subdivision, passport);
        employeeDAO.add(employee);
    }


    private void editEmployee(String lastName, String firstName, String middleName,
                              Position position, Integer salary, Integer percent,
                              Department department, Subdivision subdivision,
                              Integer passport) {
        updateEmployee.setLastName(lastName);
        updateEmployee.setFirstName(firstName);
        updateEmployee.setMiddleName(middleName);
        updateEmployee.setPosition(position);
        updateEmployee.setSalary(salary);
        updateEmployee.setOverTimePercent(percent);
        updateEmployee.setDepartment(department);
        updateEmployee.setSubdivision(subdivision);
        updateEmployee.setPassport(passport);

        employeeDAO.update(updateEmployee);
    }


    private boolean validate(String lastName, String firstName, String middleName,
                             Position position, String salary, String percent,
                             Department department, Subdivision subdivision,
                             String passport) {
        return position != null && department != null &&
                Validate.oneWord(lastName) && Validate.oneWord(firstName) &&
                Validate.oneWord(middleName) && Validate.number(salary) &&
                Validate.number(percent) && Integer.parseInt(percent) < 100 &&
                Validate.number(passport) && passport.length() == 10;
    }


    public static EmployeeFormController addEmployee() throws Exception {
        EmployeeFormController controller = new EmployeeFormController(ActionType.ADD);
        controller.showWindow();

        return controller;
    }

    public static EmployeeFormController editEmployee(Employee employee) throws Exception {
        EmployeeFormController controller = new EmployeeFormController(ActionType.EDIT);
        controller.updateEmployee(employee);
        controller.showWindow();

        return controller;
    }


    private void updateEmployee(Employee employee) {
        last_name.setText(employee.getLastName());
        first_name.setText(employee.getFirstName());
        middle_name.setText(employee.getMiddleName());
        positionBox.getSelectionModel().select(employee.getPositionId() - 1);
        salary.setText(employee.getSalary().toString());
        over_time_percent.setText(employee.getOverTimePercent().toString());
        departmentBox.getSelectionModel().select(employee.getDepartmenId() - 1);
        if (employee.getSubdivision() != null) {
            subdivisionBox.getSelectionModel().select(employee.getSubdivisionId() - 1);
        }
        passport.setText(employee.getPassport().toString());

        updateEmployee = employee;
    }


    private boolean unique(Employee employee, ObservableList<Employee> list) {
        Boolean flag = false;
        for (Employee e : list) {
            if (employee.getLastName().equals(e.getLastName()) &&
                    employee.getFirstName().equals(e.getFirstName()) &&
                    employee.getMiddleName().equals(e.getMiddleName())) {
                return true;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    public void setOnChangeListener(OnChangeListener listener) {
        this.listener = listener;
    }


    private void showWindow() {
        stage.show();
    }
}
