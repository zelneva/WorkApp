package controllers;

import dao.SubdivisionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Subdivision;

public class SubdivisionController {
    private ObservableList<Subdivision> subdivisionsData = FXCollections.observableArrayList();

    @FXML
    private TableView<Subdivision> tableSubdivision;

    @FXML
    private TableColumn<Subdivision, String> name;

    @FXML
    private TableColumn<Subdivision, String> department;

    @FXML
    private Button update;

    private SubdivisionDAO subdivisionDAO = new SubdivisionDAO();

    @FXML
    private void initialize() throws Exception {
        initData();
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        department.setCellValueFactory(new PropertyValueFactory<>("departmentName"));

        update.setOnAction(event -> {
            try {
                subdivisionsData.clear();
                subdivisionsData.addAll(subdivisionDAO.findSubdivision());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        tableSubdivision.setItems(subdivisionsData);
    }

    private void initData() throws Exception {
        subdivisionsData.addAll(subdivisionDAO.findSubdivision());
    }
}
