import com.example.hesperiaautomechanic.HesperiaAutoMechanic;
import db.JDBC;
import model.Appointment;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class UnitTests {
HesperiaAutoMechanic app = new HesperiaAutoMechanic();

public UnitTests() throws SQLException {
}

@Before
public void init() {
    app.init();
}

/**
 * This is a method that checks log in.  This method checks that the user is
 * able to log in using a valid login, and checks the connection to the database.
 *
 * @throws SQLException
 */
@Test
public void testLoginUser() throws SQLException {
    int User_ID = JDBC.checkLogin("test", "test");
    assertEquals(1, User_ID);

}

/**
 * This is a test method.  This method enters an appointment. It checks it for overlap, and then checks
 * that the 15-min notification works.
 *
 * @throws SQLException
 */
@Test
public void testNextAppointment() throws SQLException {
    Appointment model = new Appointment();
    LocalDate date = LocalDate.of(2023, 12, 5);
    model.setStart(date.atTime(10, 0));
    model.setEnd(date.atTime(10, 30));
    model.setTitle("TestAppointment");
    model.setContact_ID(1);
    model.setCustomer_ID(1);
    model.setUser_ID(1);
    model.setDescription("TestDescription");
    model.setType("TestType");
    model.setLocation("TestLocation");

    app.AddAppointment(model);

    var appointment = app.GetNextAppointment(date.atTime(9, 50), 1);

    assertEquals(appointment.getType(), "TestType");
}
}


