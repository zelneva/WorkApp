import DB.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;

public class App extends Application {

    public App() {
    }

    public static void initialize() throws Exception {
        try {
//            DBConnection con = new DBConnection();
//            con.getConnection();
            try {

            } finally {
//                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("view/app.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Work");
            primaryStage.show();
        }catch (Exception e){
            System.out.print(e.getMessage()+"!");
        }

    }


    public static void main(String[] args) throws Exception {
        //initialize();
        launch(args);
    }
}