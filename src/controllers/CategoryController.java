package controllers;

import dao.CategoryDAO;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Category;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


public class CategoryController {

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

    private CategoryDAO c = new CategoryDAO();

    @FXML
    private void initialize() throws Exception {
        if (tableCategory == null) return;

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        percent.setCellValueFactory(new PropertyValueFactory<>("percent"));

        initData();

        add.setOnAction(event -> {
            try {
                CategoryFormController.addCategory().setOnChangeListener(this::update);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



        searchFieldComponent.textProperty().addListener((observable, oldValue, newValue) -> {
            applyFilter();
        });
    }

    private void setList(ObservableList<Category> list) {
        tableCategory.getItems().clear();
        ObservableList<Category> items = FXCollections.observableArrayList(list);
        tableCategory.setItems(items);
    }


    private void initData() throws Exception {
        categoriesData.addAll(c.findCategory());
        setList(categoriesData);
        searchFieldComponent.setText("");
    }


    private void update() {
        try {
            categoriesData.clear();
            categoriesData.addAll(c.findCategory());
            setList(categoriesData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void applyFilter() {
        String searchWord = searchFieldComponent.getText().toLowerCase().trim();
        ObservableList<Category> filterList = this.categoriesData
                .stream().filter(i -> i.getName().toLowerCase().trim().contains(searchWord))
                .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
        setList(filterList);
    }


    public void deleteCategory(javafx.event.ActionEvent actionEvent) throws Exception {
        int index = tableCategory.getSelectionModel().getFocusedIndex();
        Integer id = tableCategory.getItems().get(index).getId();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удаление категории");
        alert.setContentText("Вы точно хотите удалить эту категорию?");
        alert.setHeaderText(null);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            c.deleteCategory(id);
        }
        update();
    }


    public void editCategory(javafx.event.ActionEvent event) throws Exception {
        int index = tableCategory.getSelectionModel().getFocusedIndex();
        Category category = tableCategory.getItems().get(index);
        CategoryFormController.editCategory(category).setOnChangeListener(this::update);
    }
}
