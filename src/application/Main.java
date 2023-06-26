
package application;

import javafx.stage.Stage;



import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
        	
            BorderPane root = FXMLLoader.load(getClass().getResource("/br/ufrn/imd/view/TelaLogin.fxml"));
            Scene scene = new Scene(root, 700, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


