import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    private static final String URL = "jdbc:mysql://localhost:3306/git_commands_db";
    private static final String USER = "root"; // Your MySQL username
    private static final String PASSWORD = ""; // Leave empty if no password

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
