package controllers;

import dao.CategoryDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Category;
import model.IModel;

import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


public class CategoryController extends IController<Category>{

    private ObservableList<Category> categoriesData = FXCollections.observableArrayList();

    @FXML
    private TableView<Category> tableCategory;

    @FXML
    private TableColumn<Category, String> name;

    @FXML
    private TableColumn<Category, Integer> percent;

    @FXML
    Button add;

    @FXML
    private TextField searchFieldComponent;

    private CategoryDAO categoryDAO = new CategoryDAO();

    @FXML
    private void initialize() {
        if (tableCategory == null) return;

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        percent.setCellValueFactory(new PropertyValueFactory<>("percent"));

        initData(categoriesData, tableCategory, categoryDAO, searchFieldComponent);

        add.setOnAction((ActionEvent event) -> {
            try {
                CategoryFormController.addCategory()
                        .setOnChangeListener(() -> update(categoriesData, categoryDAO, tableCategory));
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
        ObservableList<Category> filterList = this.categoriesData
                .stream().filter(i -> i.getName().toLowerCase().trim().contains(searchWord))
                .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
        setList(filterList, tableCategory);
    }


    public void deleteCategory(javafx.event.ActionEvent actionEvent) {
        int index = tableCategory.getSelectionModel().getFocusedIndex();
        Integer id = tableCategory.getItems().get(index).getId();
        delete(id, tableCategory, categoryDAO, categoriesData);
    }


    public void editCategory(javafx.event.ActionEvent event) throws Exception {
        int index = tableCategory.getSelectionModel().getFocusedIndex();
        Category category = tableCategory.getItems().get(index);
        CategoryFormController.editCategory(category)
                .setOnChangeListener(() -> update(categoriesData, categoryDAO, tableCategory));
    }
}
