package tests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import java.sql.*;
public class DataVerificationTest {
    private static Connection connection;
    private static ResultSet results;
    private static Statement statement;
    @BeforeTest
    public void initiateConnection() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost/postgres",
                "Kosta6808", "QWEdsa!1234");
    }
    public void executeQuery(String query) throws SQLException {
        initiateConnection();
        statement = connection.createStatement();
        results = statement.executeQuery(query);
    }
    @Test
    public void verifyOrderDetails() throws SQLException {
        executeQuery("select * from orders where item_sku='ABCD0006'");
        System.out.println(results);
        while (results.next()){
            assertEquals(results.getString("Quantity"), "1");
            assertEquals(results.getString("order_id"), "PR125");
        }
    }
    @AfterTest
    public void closeConnection() throws SQLException {
        results.close();
        statement.close();
    }
}