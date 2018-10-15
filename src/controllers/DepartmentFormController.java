package controllers;

import dao.DepartmentDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.ActionType;
import model.Category;
import model.Department;

import java.awt.*;
import java.io.IOException;

public class DepartmentFormController extends FormController {

    @FXML
    private TextField name;

    @FXML
    private TextField type;

    @FXML
    private ComboBox<Category> comboBox;

    @FXML
    private Button saveBtn;

    private OnChangeListener listener;
    private Stage stage;

    DepartmentDAO departmentDAO = new DepartmentDAO();
    Department updateDepartment = new Department();


    public DepartmentFormController(ActionType type) throws IOException {

        loadWindow("../view/department_form.fxml");

    }


    public static void addDepartment() {
    }


    public static void editDepartment(Department department) {
    }

}
