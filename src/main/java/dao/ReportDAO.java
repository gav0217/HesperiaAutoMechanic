package dao;

import db.JDBC;
import helper.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.CustomerSchedule;
import model.MonthType;
import model.TotalAppointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZoneOffset;
/**
 * This is the Report DAO class. This is the interface for the implementation of the report class.
 *
 * @author Gavril Fofiu
 */
public class ReportDAO {
/**
 * This method loads the all customers on schedule table view. Loads all the information into the proper
 * colum.
 *
 * @param customerName loads all Customers with appointments.
 * @return a customer observable list name allCustomerSchedule.
 * @throws SQLException
 */
public static ObservableList<CustomerSchedule> getAllCustomerSchedule(String customerName) throws SQLException {
    ObservableList<CustomerSchedule> customerScheduleObservableList = FXCollections.observableArrayList();
    String query =
            "SELECT appointments.Appointment_ID, appointments.Title, appointments.Type, appointments.Description, " +
                           "appointments.Location, appointments.Start, appointments.End, customers.Customer_Name, customers.Customer_ID " +
                           "from appointments " +
                           "INNER JOIN customers ON " +
                           "appointments.Customer_ID = customers.Customer_ID";
    if((!customerName.equals("")) && (!customerName.equals("All")))
    {
        query += " WHERE customers.Customer_Name LIKE '" + customerName + "'";
    }
    PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        CustomerSchedule customerSchedule = new CustomerSchedule();
        customerSchedule.setAppointment_ID(rs.getInt("Appointment_ID"));
        customerSchedule.setTitle(rs.getString("Title"));
        customerSchedule.setType(rs.getString("Type"));
        customerSchedule.setDescription(rs.getString("Description"));
        customerSchedule.setLocation(rs.getString("Location"));
        customerSchedule.setStart(Utils.getLocalTimeFromTimestamp(rs.getTimestamp("Start")));
        customerSchedule.setEnd(Utils.getLocalTimeFromTimestamp(rs.getTimestamp("End")));
        customerSchedule.setCustomer_Name(rs.getString("Customer_Name"));
        customerSchedule.setCustomer_ID(rs.getInt("Customer_ID"));
        customerScheduleObservableList.add(customerSchedule);
    }

    return customerScheduleObservableList;
}


/**
 * This is a get method. Gets all the appointments based on the month and type from the database.
 * Loads it in the observable list AllMonthType.
 *
 * @return a list named AllMonthType.
 * @throws SQLException
 */
public static ObservableList<MonthType> getAllMonthType() throws SQLException {
    ObservableList<MonthType> monthTypesObservableList = FXCollections.observableArrayList();
    String query = "Select DATE_FORMAT(CAST(start as DATE), '%m') as month, type, COUNT(*) as total from appointments group by type, month";
    PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        MonthType monthType = new MonthType();
        monthType.setMonth(Utils.getMonthName(rs.getInt("Month")));
        monthType.setType(rs.getString("Type"));
        monthType.setTotal(rs.getInt("Total"));
        monthTypesObservableList.add(monthType);
    }

    return monthTypesObservableList;
}

/**
 * This is a get method. This gets the total appointments for each customer from the database.
 * Loads it in the observable list AllTotalAppointment.
 *
 * @return a list AllTotalAppointments.
 * @throws SQLException
 */
public static ObservableList<TotalAppointment> getAllTotalAppointment() throws SQLException {
    ObservableList<TotalAppointment> totalAppointmentsObservableList = FXCollections.observableArrayList();
    String query = "SELECT COUNT(appointments.Customer_ID) as Amount, customers.Customer_Name, customers.Customer_ID from appointments " +
                           "INNER JOIN customers ON " +
                           "appointments.Customer_ID = customers.Customer_ID Group by  customers.Customer_ID";
    PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        TotalAppointment totalAppointment = new TotalAppointment();
        totalAppointment.setCustomer_ID(rs.getInt("Customer_ID"));
        totalAppointment.setCustomer_Name(rs.getString("Customer_Name"));
        totalAppointment.setAmount(rs.getInt("Amount"));
        totalAppointmentsObservableList.add(totalAppointment);
    }

    return totalAppointmentsObservableList;
}

}