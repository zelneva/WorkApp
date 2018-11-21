package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class SignController {

    @FXML
    private Button signin;

    @FXML
    private Button signup;

    @FXML
    public void initialize() {

        signin.setOnAction(event -> {
            try {
                new LoginController();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        signup.setOnAction(event -> {
            try {
                new SignUpController();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
