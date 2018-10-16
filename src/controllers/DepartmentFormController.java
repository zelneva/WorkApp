package controllers;

import dao.CategoryDAO;
import dao.DepartmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ActionType;
import model.Category;
import model.Department;
import model.Validate;


public class DepartmentFormController extends FormController {

    @FXML
    TextField name;

    @FXML
    TextField type;

    @FXML
    ComboBox<Category> comboBox;

    @FXML
    Button saveBtn;

    private OnChangeListener listener;
    private Stage stage = new Stage();

    DepartmentDAO departmentDAO = new DepartmentDAO();
    CategoryDAO categoryDAO = new CategoryDAO();
    Department updateDepartment = new Department();


    public DepartmentFormController(ActionType actionType) throws Exception {

        loadWindow("../view/department_form.fxml", stage, this);
        addHeader(actionType, stage);

        ObservableList<Category> categoryList = FXCollections.observableArrayList(categoryDAO.find());
        comboBox.setItems(categoryList);

        saveBtn.setOnAction(e -> {
            String departmentName = name.getText();
            String departmentType = type.getText();
            Category category = comboBox.getValue();

            if (!this.validate(departmentName, departmentType, category)) {
                showAlert();
                return;
            }

            if (actionType == ActionType.ADD) {
                if(unique(new Department(departmentName, departmentType, category),departmentDAO.find())){
                    showAlertUnique();
                    return;
                }
                _createDepartment(departmentName, departmentType, category);
            }

            if (actionType == ActionType.EDIT) {
                _editDepartment(departmentName, departmentType, category);
            }

            if (listener != null) {
                listener.onChange();
            }

            stage.close();
        });
    }

    private void _createDepartment(String name, String type, Category category) {
        Department department = new Department(name, type, category);
        departmentDAO.add(department);
    }


    private void _editDepartment(String name, String type, Category category) {
        updateDepartment.setName(name);
        updateDepartment.setType(type);
        updateDepartment.setCategory(category);

        departmentDAO.update(updateDepartment);
    }


    private boolean validate(String name, String type, Category category) {
        return category != null && (Validate.oneWord(name)
                || Validate.twoWords(name))
                && Validate.oneWord(type);
    }


    public static DepartmentFormController addDepartment() throws Exception {
        DepartmentFormController controller = new DepartmentFormController(ActionType.ADD);
        controller.showWindow();

        return controller;
    }


    public static DepartmentFormController editDepartment(Department department) throws Exception {
        DepartmentFormController controller = new DepartmentFormController(ActionType.EDIT);
        controller.updateDepartment(department);
        controller.showWindow();

        return controller;
    }


    private void updateDepartment(Department department) {
        name.setText(department.getName());
        type.setText(department.getType());
        comboBox.getSelectionModel().select(department.getCategoryId()-1);
        updateDepartment = department;
    }

    private boolean unique(Department department, ObservableList<Department> list) {
        Boolean flag = false;
        for (Department d : list) {
            if (department.getName().equals(d.getName())) {
                return true;
            } else {
                flag =  false;
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
