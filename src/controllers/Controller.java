package controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Controller() {
    }

    Stage openWindow(String path, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(path));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
            return stage;
        } catch (Exception var6) {
            System.out.println("Cannot load modal");
            System.out.println(var6.getMessage());
            var6.printStackTrace();
            return null;
        }
    }

    void init() {
    }

    public void initialize(URL location, ResourceBundle resources) {
        this.init();
    }
}

