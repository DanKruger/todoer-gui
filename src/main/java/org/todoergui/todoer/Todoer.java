package org.todoergui.todoer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class Todoer extends Application {
    private static Stage primaryStage;
    private static Backend server;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Todoer.class.getResource("note-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        changeScene("note-view.fxml", "Todoer ~ Notes");
        primaryStage.setTitle("Todoer ~ Notes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void changeScene(String viewPath, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Todoer.class.getResource(viewPath));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle(title);
    }

    public static void main(String[] args) {
        try {
            server = new Backend( "jdbc:sqlite:./todoer.sqlite" );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        launch();
    }
    public static Backend getServer() {
        return server;
    }
}