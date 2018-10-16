package controllers;

import dao.DepartmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Category;
import model.Department;

import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class DepartmentController extends  IController<Department>{

    private ObservableList<Department> departmentsData = FXCollections.observableArrayList();

    @FXML
    private TableView<Department> tableDepartment;

    @FXML
    private TableColumn<Department, String> name;

    @FXML
    private TableColumn<Department, String> type;

    @FXML
    private TableColumn<Department, Category> category;

    @FXML
    private TextField searchFieldComponent;

    @FXML
    private Button add;

    private DepartmentDAO departmentDAO = new DepartmentDAO();

    @FXML
    private void initialize(){

        if (tableDepartment == null) return;

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        category.setCellValueFactory(new PropertyValueFactory<>("categoryName"));

        initData(departmentsData, tableDepartment, departmentDAO, searchFieldComponent);

        add.setOnAction(event -> {
            try {
                DepartmentFormController.addDepartment()
                        .setOnChangeListener(() -> update(departmentsData, departmentDAO, tableDepartment));
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
        ObservableList<Department> filterList = this.departmentsData
                .stream().filter(i -> i.getName().toLowerCase().trim().contains(searchWord))
                .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
        setList(filterList, tableDepartment);
    }


    public void deleteDepartment(ActionEvent event) {
        int index = tableDepartment.getSelectionModel().getFocusedIndex();
        Integer id = tableDepartment.getItems().get(index).getId();
        delete(id, tableDepartment, departmentDAO, departmentsData);
    }


    public void editDepartment(ActionEvent event) throws  Exception{
        int index = tableDepartment.getSelectionModel().getFocusedIndex();
        Department department = tableDepartment.getItems().get(index);
        DepartmentFormController.editDepartment(department)
                .setOnChangeListener(() -> update(departmentsData, departmentDAO, tableDepartment));
    }
}
