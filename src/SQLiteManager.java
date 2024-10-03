import java.sql.*;

public class SQLiteManager {
    private static final String URL = "jdbc:sqlite:contacts.db";

    // Create table if not exists
    public static void createDatabase() {
        try (Connection conn = DriverManager.getConnection(URL)) {
            String sql = "CREATE TABLE IF NOT EXISTS contacts (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, age INTEGER, email TEXT, phone TEXT, address TEXT)";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("Database initialized.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Save to SQLite
    public static void saveToSQLite(ListOfContacts contactListApp) {
        try (Connection conn = DriverManager.getConnection(URL)) {
            String sql = "INSERT INTO contacts (name, age, email, phone, address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (Person person : contactListApp.getContactList()) {
                pstmt.setString(1, person.getName());
                pstmt.setInt(2, person.getAge());
                pstmt.setString(3, person.getMail());
                pstmt.setString(4, person.getPhone());
                pstmt.setString(5, person.getAddress());
                pstmt.executeUpdate();
            }
            System.out.println("Contacts saved to SQLite.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load from SQLite
    public static ListOfContacts loadFromSQLite() {
        ListOfContacts contactListApp = new ListOfContacts();
        try (Connection conn = DriverManager.getConnection(URL)) {
            String sql = "SELECT * FROM contacts";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                Person person = new Person(age, name, email, phone, address);
                contactListApp.addPerson(person);
            }
            System.out.println("Contacts loaded from SQLite.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactListApp;
    }
}
