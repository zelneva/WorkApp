package controllers;

import dao.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

public class SignUpController {
    private Stage stage = new Stage();

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    @FXML
    private ComboBox<String> roleBox;

    @FXML
    private Button button;

    UserDAO userDAO = new UserDAO();

    ObservableList<String> roleList = FXCollections.observableArrayList("Администратор","Бухгалтер", "Менеджер");

    public SignUpController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/signup.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
        roleBox.setItems(roleList);

        button.setOnAction(event -> {
            String name = login.getText();
            String pass = password.getText();
            String roll = roleBox.getValue();

            User newUser = new User(name, pass, roll);
            userDAO.add(newUser);

            switch (roll) {
                case "Бухгалтер":
                    FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("../view/employee.fxml"));
                    fxmlLoader1.setController(new EmployeeController());
                    Parent root1 = null;
                    try {
                        root1 = fxmlLoader1.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage stage1 = new Stage();
                    stage1.setScene(new Scene(root1));
                    stage1.show();
                    stage.close();
                    break;
                case "Администратор":
                    try {
                        new Controller();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage.close();
                    break;
                case "Менеджер":
                    try {
                        new ManagerController();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage.close();
                    break;
                default:
                    break;
            }
        });
    }
}
