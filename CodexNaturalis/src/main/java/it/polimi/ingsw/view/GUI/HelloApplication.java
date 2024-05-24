package it.polimi.ingsw.view.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        URL fxmlLocation = HelloApplication.class.getResource("/it.polimi.ingsw.view.GUI/fxml/hello-view.fxml");
        if (fxmlLocation == null) {
            System.out.println("FXML file not found!");
            return;
        } else {
            System.out.println("FXML file found at: " + fxmlLocation);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
