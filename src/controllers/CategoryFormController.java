package controllers;

import dao.CategoryDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ActionType;
import model.Category;
import model.IModel;
import model.Validate;

import java.io.IOException;

public class CategoryFormController extends FormController {

    @FXML
    TextField name;

    @FXML
    TextField percent;

    @FXML
    Button saveBtn;

    private OnChangeListener listener;
    private Stage stage = new Stage();

    CategoryDAO categoryDAO = new CategoryDAO();
    Category newCategory = new Category();


    public CategoryFormController(ActionType actionType) throws IOException {

        loadWindow("../view/category_form.fxml", stage, this);
        addHeader(actionType, stage);

        saveBtn.setOnAction(e -> {
            String categoryName = name.getText();
            String categoryPercent = percent.getText();

            if (!this.validate(categoryName, categoryPercent)) {
                showAlert();
                return;
            }


            if (actionType == ActionType.ADD) {
                if(unique(new Category(categoryName, Integer.parseInt(categoryPercent)), categoryDAO.find())){
                    showAlertUnique();
                    return;
                }
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
        categoryDAO.add(category);
    }


    private void _updateCategory(String name, String percent) {
        newCategory.setName(name);
        newCategory.setPercent(Integer.parseInt(percent));

        try {
            categoryDAO.update(newCategory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean validate(String name, String percent) {
        return Validate.category(name) &&
                Validate.number(percent) &&
                Integer.parseInt(percent) > 0 &&
                Integer.parseInt(percent) < 100;
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

    private void showWindow() {
        stage.show();
    }

    private boolean unique(Category category, ObservableList<Category> list) { //true - если есть такой элемент
        Boolean flag = false;
        for (Category c : list) {
            if (category.getName().equals(c.getName())) {
                return true;
            } else {
                flag = false;
            }
        }
        return flag;
    }
}
