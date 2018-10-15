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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class DepartmentController {

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
    private void initialize() throws Exception {

        if (tableDepartment == null) return;

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        category.setCellValueFactory(new PropertyValueFactory<>("categoryName"));

        initData();

        add.setOnAction(event -> {
            try {
                DepartmentFormController.addDepartment().setOnChangeListener(this::update);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        searchFieldComponent.textProperty().addListener((observable, oldValue, newValue) -> {
            applyFilter();
        });

    }


    private void initData() throws Exception {
        departmentsData.addAll(departmentDAO.findDepartment());
        setList(departmentsData);
        searchFieldComponent.setText("");
    }


    private void setList(ObservableList<Department> list) {
        tableDepartment.getItems().clear();
        ObservableList<Department> items = FXCollections.observableArrayList(list);
        tableDepartment.setItems(items);
    }


    private void update() {
        try {
            departmentsData.clear();
            departmentsData.addAll(departmentDAO.findDepartment());
            setList(departmentsData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void applyFilter() {
        String searchWord = searchFieldComponent.getText().toLowerCase().trim();
        ObservableList<Department> filterList = this.departmentsData
                .stream().filter(i -> i.getName().toLowerCase().trim().contains(searchWord))
                .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
        setList(filterList);
    }

    public void deleteCategory(ActionEvent event) throws Exception {
        int index = tableDepartment.getSelectionModel().getFocusedIndex();
        Integer id = tableDepartment.getItems().get(index).getId();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удаление категории");
        alert.setContentText("Вы точно хотите удалить эту категорию?");
        alert.setHeaderText(null);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            departmentDAO.deleteDepartment(id);
        }
        update();
    }


    public void editCategory(ActionEvent event) {
        int index = tableDepartment.getSelectionModel().getFocusedIndex();
        Department department = tableDepartment.getItems().get(index);
        DepartmentFormController.editDepartment(department).setOnChangeListener(this::update);
    }
}
