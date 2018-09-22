package controllers;

import dao.CategoryDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Category;


public class CategoryController {

    private ObservableList<Category> categoriesData = FXCollections.observableArrayList();

    @FXML
    private TableView<Category> tableCategory;

    @FXML
    private TableColumn<Category, String> name;

    @FXML
    private TableColumn<Category, Integer> percent;

    @FXML
    private Button update;

    private CategoryDAO c = new CategoryDAO();

    @FXML
    private void initialize() throws Exception {
        initData();
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        percent.setCellValueFactory(new PropertyValueFactory<Category, Integer>("percent"));

        update.setOnAction(event -> {
            try {
                categoriesData.clear();
                categoriesData.addAll(c.findCategory());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        tableCategory.setItems(categoriesData);
    }

    private void initData() throws Exception {
        categoriesData.addAll(c.findCategory());
    }
}
