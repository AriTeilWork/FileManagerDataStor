import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public class JSONManager {
    public static void saveToJSON(ListOfContacts contactListApp) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("contacts.json")) {
            gson.toJson(contactListApp, writer);
            System.out.println("Contacts saved to JSON.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ListOfContacts loadFromJSON() {
        Gson gson = new Gson();
        ListOfContacts contacts = new ListOfContacts();
        try (FileReader reader = new FileReader("contacts.json")) {
            Type contactListType = new TypeToken<ListOfContacts>() {}.getType();
            contacts = gson.fromJson(reader, contactListType);
            System.out.println("Contacts loaded from JSON.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contacts;
    }
}
