package controllers;

import dao.CategoryDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ActionType;
import model.Category;
import model.Validate;

import java.io.IOException;

public class CategoryFormController {

    @FXML
    TextField name;

    @FXML
    TextField percent;

    @FXML
    Button saveBtn;

    private OnChangeListener listener;
    private Stage stage;

    CategoryDAO categoryDAO = new CategoryDAO();
    Category newCategory = new Category();


    public CategoryFormController(ActionType actionType) throws IOException {

        loadWindow();

        if (actionType == ActionType.ADD) {
            stage.setTitle("Добавление категории");
        } else if (actionType == ActionType.EDIT) {
            stage.setTitle("Редактирование категории");
        }

        saveBtn.setOnAction(e -> {
            String categoryName = name.getText();
            String categoryPercent = percent.getText();

            if (!this.validate(categoryName, categoryPercent)) {
                showAlert();
                return;
            }

            if (actionType == ActionType.ADD) {
                _createCategory(categoryName, categoryPercent);
            }

            if (actionType == ActionType.EDIT) {
                _updateCategory(categoryName, categoryPercent);
            }

            if (listener != null) {
                listener.onChange();
            }

            stage.close();
        });
    }


    private void _createCategory(String name, String percent) {
        Category category = new Category(name, Integer.parseInt(percent));
        categoryDAO.addCategory(category);
    }


    private void _updateCategory(String name, String percent) {
        newCategory.setName(name);
        newCategory.setPercent(Integer.parseInt(percent));

        try {
            categoryDAO.updateCategory(newCategory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean validate(String name, String percent) {
        return  Validate.category(name) &&
                Validate.number(percent) &&
                Integer.parseInt(percent) > 0 &&
                Integer.parseInt(percent) < 100;
    }

    private void showAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка");
        alert.setHeaderText("ВНИМАНИЕ");
        alert.setContentText("Вы ввели некорректные данные");
        alert.show();
    }


    public static CategoryFormController addCategory() throws IOException {
        CategoryFormController controller = new CategoryFormController(ActionType.ADD);
        controller.showWindow();

        return controller;
    }


    public static CategoryFormController editCategory(Category category) throws IOException {
        CategoryFormController controller = new CategoryFormController(ActionType.EDIT);
        controller.updateCategory(category);
        controller.showWindow();

        return controller;
    }


    public void setOnChangeListener(OnChangeListener listener) {
        this.listener = listener;
    }


    private void updateCategory(Category category) {
        name.setText(category.getName());
        percent.setText(category.getPercent().toString());
        newCategory = category;
    }


    private void loadWindow() throws IOException {
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/category_form.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }


    private void showWindow() {
        stage.show();
    }
}
