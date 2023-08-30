package com.example.hesperiaautomechanic;

import dao.AppointmentDAO;
import db.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
    launch();
}

/**
 * This method displays a message.  The message is a confirmation that the application is starting.
 */
@Override
public void init() {
    JDBC.makeConnection();
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

/**
 * This is a time method.  This method gets the local time and converts it into the UTC time.
 *
 * @param date    current date
 * @param user_id current user
 * @return returns the current date and time in UTC zone.
 * @throws SQLException
 */
public Appointment GetNextAppointment(LocalDateTime date, int user_id) throws SQLException {
    var dateTime = date.atZone(ZoneId.systemDefault()).toInstant().atZone(ZoneId.of("UTC"));
    var utcDate = new java.util.Date(dateTime.getYear() - 1900, dateTime.getMonth().ordinal(), dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute());
    return AppointmentDAO.getNextAppointment(utcDate, user_id);
}

/**
 * This method checks for overlaps. The method gets the start of the appointment,
 * and sees if there are any overlying appointments.
 *
 * @param model the model for appointments.
 * @throws SQLException
 */
public void AddAppointment(Appointment model) throws SQLException {

    if (!AppointmentDAO.isOverlap(model.getStart(), model.getEnd(), 0)) {
        AppointmentDAO.AddAppointment(model);
    }

}


}