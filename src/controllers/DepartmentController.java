package controllers;

import dao.DepartmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Department;

public class DepartmentController {

    private ObservableList<Department> departmentsData = FXCollections.observableArrayList();

    @FXML
    private TableView<Department> tableDepartment;

    @FXML
    private TableColumn<Department, String> name;

    @FXML
    private TableColumn<Department, String> type;

    @FXML
    private TableColumn<Department, String> category;

    @FXML
    private Button update;

    private DepartmentDAO departmentDAO = new DepartmentDAO();

    @FXML
    private void initialize() throws Exception {
        initData();
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));

        update.setOnAction(event -> {
            try {
                departmentsData.clear();
                departmentsData.addAll(departmentDAO.findDepartment());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        tableDepartment.setItems(departmentsData);
    }

    private void initData() throws Exception {
        departmentsData.addAll(departmentDAO.findDepartment());
    }
}
