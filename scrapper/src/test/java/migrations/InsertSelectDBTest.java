package migrations;

import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static org.assertj.core.api.Assertions.assertThat;

public class InsertSelectDBTest extends IntegrationTest {
//    private static Statement statement;
//    private final String SQL_INSERT = "INSERT INTO links (id, link, upd_at, checked_at) VALUES (1, 'http://example.com', now(), now())";
//
//
//    @BeforeAll
//    public static void setUp() throws Exception {
//        statement = DriverManager.getConnection(
//            POSTGRES.getJdbcUrl(),
//            POSTGRES.getUsername(),
//            POSTGRES.getPassword()
//        ).createStatement();
//    }
//
//    @AfterAll
//    public static void tearDown() throws Exception {
//        statement.close();
//    }
//
//    @BeforeEach
//    public void clearTable() throws SQLException {
//        statement.executeUpdate("DELETE FROM links");
//    }
//
//    @Test
//    public void insertTest() throws SQLException {
//        int result = statement.executeUpdate(SQL_INSERT);
//        assertThat(result).isEqualTo(1);
//    }
//
//    @Test
//    public void selectTest() throws SQLException {
//        statement.executeUpdate(SQL_INSERT);
//        ResultSet resultSet = statement.executeQuery("SELECT * FROM links");
//        assertThat(resultSet.next()).isTrue();
//        assertThat(resultSet.getString("link")).isEqualTo("http://example.com");
//        assertThat(resultSet.getInt("chat_id")).isEqualTo(1);
//    }
}
