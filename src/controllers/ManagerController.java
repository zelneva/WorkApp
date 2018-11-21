package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerController {
    Stage stage = new Stage();

    public ManagerController() throws IOException {
        Group root = new Group();
        Scene scene = new Scene(root, 700, 600, Color.WHITE);
        TabPane tabPane = new TabPane();
        BorderPane borderPane = new BorderPane();
        for (int i = 0; i < 3; i++) {
            Tab tab = new Tab();
            switch (i){
                case 0: createTab(tab, "../view/department.fxml", new DepartmentController() );
                    tab.setText("Отделы");
                    break;
                case 1: createTab(tab, "../view/subdivision.fxml", new SubdivisionController() );
                    tab.setText("Подразделения");
                    break;
                case 2: createTab(tab, "../view/position.fxml", new PositionController() );
                    tab.setText("Должность");
                    break;
                default: break;
            }


            tabPane.getTabs().add(tab);
        }
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);
        stage.setScene(scene);
        stage.show();
    }


    private void createTab(Tab tab, String path, IController controller) throws IOException {
        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource(path));
        fxmlLoader1.setController(controller);
        Parent root1 = fxmlLoader1.load();
        tab.setContent(root1);
    }
}
