package controllers;

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

public class LoginController {

    private Stage stage = new Stage();

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Button button;

    UserDAO userDAO = new UserDAO();

    public LoginController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/login.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();

        button.setOnAction(event -> {
            String name = login.getText();
            String pass = password.getText();

            Boolean flag = true;

            for (User user : userDAO.find()) {
                if (name.equals(user.getName()) && pass.equals(user.getPassword())) {
                    flag = true;
                    switch (user.getRole()) {
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
                            stage.close();
                            break;

                    }
                    if (flag == true) {
                        break;
                    }
                } else {
                    flag = false;
                }
            }

            if (flag == false) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка");
                alert.setHeaderText("ВНИМАНИЕ");
                alert.setContentText("Такого пользователя нет");
                alert.show();
            }

        });
    }

    @FXML
    public void initialize() {
    }
}
