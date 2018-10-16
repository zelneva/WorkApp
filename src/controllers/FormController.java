package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.ActionType;
import model.IModel;

import java.io.IOException;

public class FormController {

    void loadWindow(String path, Stage stage, Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    void showAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка");
        alert.setHeaderText("ВНИМАНИЕ");
        alert.setContentText("Вы ввели некорректные данные");
        alert.show();
    }

    void showAlertUnique(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка");
        alert.setHeaderText("ВНИМАНИЕ");
        alert.setContentText("Элемент с таким названием уже существует");
        alert.show();
    }

    void addHeader(ActionType actionType, Stage stage){
        if (actionType == ActionType.ADD) {
            stage.setTitle("Добавление ");
        } else if (actionType == ActionType.EDIT) {
            stage.setTitle("Редактирование ");
        }
    }


}
