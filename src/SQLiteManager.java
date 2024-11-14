import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLiteManager {

    public static void saveToSQLite(ListOfContacts contactListApp) {
        String url = "jdbc:sqlite:contacts.db"; // Ensure this path is correct

        String createTableSQL = "CREATE TABLE IF NOT EXISTS contacts (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "age INTEGER NOT NULL," +
                "email TEXT NOT NULL," +
                "phone TEXT NOT NULL," +
                "address TEXT NOT NULL" +
                ");";

        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Establish connection
            try (Connection conn = DriverManager.getConnection(url)) {
                if (conn != null) {
                    // Create table if not exists
                    conn.createStatement().execute(createTableSQL);

                    // Insert contacts into database
                    for (Person person : contactListApp.getContactList()) {
                        String insertSQL = "INSERT INTO contacts(name, age, email, phone, address) VALUES(?, ?, ?, ?, ?)";
                        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                            pstmt.setString(1, person.getName());
                            pstmt.setInt(2, person.getAge());
                            pstmt.setString(3, person.getMail());
                            pstmt.setString(4, person.getPhone());
                            pstmt.setString(5, person.getAddress());
                            pstmt.executeUpdate();
                        }
                    }
                    System.out.println("Contacts saved to SQLite.");
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }

        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC Driver not found.");
            e.printStackTrace();
        }
    }
}
