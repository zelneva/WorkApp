package controllers;

import dao.DepartmentDAO;
import dao.SubdivisionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

public class SubdivisionFormController extends FormController {

    @FXML
    TextField name;

    @FXML
    ComboBox<Department> comboBox;

    @FXML
    Button saveBtn;

    private OnChangeListener listener;
    private Stage stage = new Stage();

    SubdivisionDAO subdivisionDAO = new SubdivisionDAO();
    DepartmentDAO departmentDAO = new DepartmentDAO();
    Subdivision updateSubdivision = new Subdivision();

    public SubdivisionFormController(ActionType actionType) throws IOException {
        loadWindow("../view/subdivision_form.fxml", stage, this);
        addHeader(actionType, stage);

        ObservableList<Department> departmentList = FXCollections.observableArrayList(departmentDAO.find());
        comboBox.setItems(departmentList);

        saveBtn.setOnAction(e -> {
            String departmentName = name.getText();
            Department department = comboBox.getValue();

            if (!this.validate(departmentName, department)) {
                showAlert();
                return;
            }

            if (actionType == ActionType.ADD) {
                if(unique(new Subdivision(departmentName, department), subdivisionDAO.find())){
                    showAlertUnique();
                    return;
                }
                createSubdivision(departmentName, department);
            }

            if (actionType == ActionType.EDIT) {
                editSubdivision(departmentName, department);
            }

            if (listener != null) {
                listener.onChange();
            }

            stage.close();
        });
    }


    private void createSubdivision(String name, Department department) {
        Subdivision subdivision = new Subdivision(name, department);
        subdivisionDAO.add(subdivision);
    }


    private void editSubdivision(String name, Department department) {
        updateSubdivision.setName(name);
        updateSubdivision.setDepartment(department);

        subdivisionDAO.update(updateSubdivision);
    }


    private boolean validate(String name, Department department) {
        return department != null &&
                (Validate.oneWord(name) || Validate.twoWords(name)
                        || Validate.threeWords(name));
    }


    public static SubdivisionFormController addSubdivision() throws Exception {
        SubdivisionFormController controller = new SubdivisionFormController(ActionType.ADD);
        controller.showWindow();
        return controller;
    }


    public static SubdivisionFormController editSubdivision(Subdivision subdivision) throws Exception {
        SubdivisionFormController controller = new SubdivisionFormController(ActionType.EDIT);
        controller.updateSubdivision(subdivision);
        controller.showWindow();
        return controller;
    }


    private void updateSubdivision(Subdivision subdivision){
        name.setText(subdivision.getName());
        comboBox.getSelectionModel().select(subdivision.getDepartmentId()-1); //TODO: !!!
        updateSubdivision = subdivision;
    }

    private boolean unique(Subdivision subdivision, ObservableList<Subdivision> list) {
        Boolean flag = false;
        for (Subdivision s : list) {
            if (subdivision.getName().equals(s.getName())) {
                return true;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    public void setOnChangeListener(OnChangeListener listner){
        this.listener = listner;
    }

    private void showWindow() {
        stage.show();
    }

}
