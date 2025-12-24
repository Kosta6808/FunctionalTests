package tests;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataVerificationTest
{
    private static final Logger logger = LoggerFactory.getLogger(DataVerificationTest.class);
    private static final Logger log = LoggerFactory.getLogger(DataVerificationTest.class);
    private static Connection connection;
    private static ResultSet results;
    private static Statement statement;
    public static Dotenv dotenv = Dotenv.load();


    @BeforeEach
    public void initiateConnection() throws SQLException
    {
        dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();
        String link = dotenv.get("PS_URL");
        String user = dotenv.get("PS_USER");
        String pass = dotenv.get("PS_PASS");

        connection = DriverManager.getConnection(link, user, pass);
    }
    public void executeQuery(String query) throws SQLException
    {
        initiateConnection();
        statement = connection.createStatement();
        results = statement.executeQuery(query);

    }
    public int executeUpdate(String query) throws SQLException
    {
        initiateConnection();
        statement = connection.createStatement();
        return statement.executeUpdate(query);
    }

    @Test
    public void verifyOrderDetails() throws SQLException
    {
        executeQuery("select * from orders where item_sku='ABCD0006'");
        log.info(String.valueOf(results));
        while (results.next()){
            assertEquals(results.getString("Quantity"), "1");
            assertEquals(results.getString("order_id"), "PR125");
        }
        results.close();
    }

    @Test
    public void verifyOrdersItemsCount() throws SQLException
    {
        executeQuery("select count(*) from orders where item_sku='ABCD0003' or item_sku='ABCD0005'");
        List<String> firstResult = new ArrayList<>();
        while (results.next())
        {
            firstResult.add(results.getString("count"));
        }

        executeQuery("select count(*) from items where item_sku='ABCD0001'");
        List<String> secondResult = new ArrayList<>();
        while (results.next())
        {
            secondResult.add(results.getString("count"));
        }
        assertEquals(firstResult, secondResult, "Результаты запросов не совпадают!");
    }

    @Test
    public void testControlCounts() throws SQLException
    {
        int res = executeUpdate("INSERT INTO orders (order_id, item_sku, quantity) VALUES ('PR130','ABCD0001', 3),('PR131','ABCD0006', 5)");
        try
        {
            logger.info("Запрос изменил {} строк", res);
        }
        catch (Exception e)
        {
            logger.error("Ошибка при выполнении: {}", e.getMessage(), e);
        }
        executeQuery("SELECT count(*) FROM items;"); // Можно сделать больше, чем одно значение(например, count(*), name) в запросе
        results.next(); // Тогда в следующей строке можно написать(с учетом типа данных)
        int count1 = results.getInt(1); // String name = results.getString(2);
        executeQuery("SELECT count(*) FROM orders;");
        results.next();
        int count2 = results.getInt(1);
        assertEquals(count1, count2, "Результаты запросов не совпадают!");
        executeUpdate("delete from orders where order_id='PR130' or order_id='PR131';");
    }

    @AfterEach
    public void closeConnection() throws SQLException {
        results.close();
        statement.close();
    }
}
