package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("../resources/MainView.fxml"));
        stage = primaryStage;
        primaryStage.setTitle("BSK - File encrypting");
        primaryStage.setScene(new Scene(root, 640.0D, 480.0D));
        primaryStage.show();

        System.out.println("Success");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
