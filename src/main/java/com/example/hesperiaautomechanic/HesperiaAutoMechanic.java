package com.example.hesperiaautomechanic;

import db.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is the class that creates the Scheduler Application.
 *
 * @author Gavril Fofiu.
 * @version 3.0
 */
public class HesperiaAutoMechanic extends Application {

/**
 * This is the main method. This is the first method that gets called when you run the Java Program.
 */
public static void main(String[] args) {
    JDBC.makeConnection();
    launch();
}

/**
 * This method displays a message.  The message is a confirmation that the application is starting.
 */
@Override
public void init() {

    System.out.println("Starting!!!");
}

/**
 * This method starts a stage.  This is the Login Menu scene that is started.
 */
@Override
public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(HesperiaAutoMechanic.class.getResource("LoginMenu.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 500, 300);
    stage.setTitle("Login Menu");
    stage.setScene(scene);
    stage.show();
}

/**
 * This method displays a message.  The message is displayed when the application is closing the in the IDE.
 */
@Override
public void stop() {

    System.out.println("Closing!!!");
}

}