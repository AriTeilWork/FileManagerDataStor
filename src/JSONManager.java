import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public class JSONManager {

    // Save contacts to JSON file
    public static void saveToJSON(ListOfContacts contactListApp) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("contacts.json")) {
            gson.toJson(contactListApp, writer);
            System.out.println("Contacts saved to JSON.");
        } catch (IOException e) {
            System.err.println("Error saving contacts to JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Load contacts from JSON file
    public static ListOfContacts loadFromJSON() {
        Gson gson = new Gson();
        ListOfContacts contacts = new ListOfContacts(); // Initialize to avoid null

        try (FileReader reader = new FileReader("contacts.json")) {
            Type contactListType = new TypeToken<ListOfContacts>() {}.getType();
            contacts = gson.fromJson(reader, contactListType);

            if (contacts == null) { // Handle case where JSON file is empty or malformed
                contacts = new ListOfContacts(); // Return an empty contact list
                System.out.println("No valid data found in JSON, initializing empty contact list.");
            } else {
                System.out.println("Contacts loaded from JSON.");
            }
        } catch (IOException e) {
            System.err.println("Error loading contacts from JSON: " + e.getMessage());
            e.printStackTrace();
        }

        return contacts;
    }
}
