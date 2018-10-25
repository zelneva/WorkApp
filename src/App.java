import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {

    public App() {
    }


    @Override
    public void start(Stage primaryStage) {
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


    public static void main(String[] args) {
        launch(args);
    }
}