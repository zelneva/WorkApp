package controllers;

import dao.SubdivisionDAO;
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
import model.Subdivision;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class SubdivisionController extends IController<Subdivision> {
    private ObservableList<Subdivision> subdivisionsData = FXCollections.observableArrayList();

    @FXML
    private TableView<Subdivision> tableSubdivision;

    @FXML
    private TableColumn<Subdivision, String> name;

    @FXML
    private TableColumn<Subdivision, String> department;

    @FXML
    private TextField searchFieldComponent;

    @FXML
    private Button add;

    private SubdivisionDAO subdivisionDAO = new SubdivisionDAO();

    @FXML
    private void initialize() {

        if (tableSubdivision == null) return;

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        department.setCellValueFactory(new PropertyValueFactory<>("departmentName"));

        initData(subdivisionsData, tableSubdivision, subdivisionDAO, searchFieldComponent);

        add.setOnAction(event -> {
            try {
                SubdivisionFormController.addSubdivision()
                        .setOnChangeListener(() -> update(subdivisionsData, subdivisionDAO, tableSubdivision));
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
        ObservableList<Subdivision> filterList = this.subdivisionsData
                .stream().filter(i -> i.getName().toLowerCase().trim().contains(searchWord))
                .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
        setList(filterList, tableSubdivision);
    }

    public void deleteSubdivision(ActionEvent event) {
        int index = tableSubdivision.getSelectionModel().getFocusedIndex();
        Integer id = tableSubdivision.getItems().get(index).getId();
        delete(id, tableSubdivision, subdivisionDAO, subdivisionsData);
    }

    public void editSubdivision(ActionEvent event) {
        int index = tableSubdivision.getSelectionModel().getFocusedIndex();
        Subdivision subdivision = tableSubdivision.getItems().get(index);
        try {
            SubdivisionFormController.editSubdivision(subdivision)
                    .setOnChangeListener(() -> update(subdivisionsData, subdivisionDAO, tableSubdivision));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
