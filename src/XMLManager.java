import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XMLManager {

    public XMLManager() {
    }

    // Save to XML file
    public static void saveToXML(ListOfContacts contactListApp, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(ListOfContacts.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            File file = new File(filePath);
            marshaller.marshal(contactListApp, file);
            System.out.println("Contacts saved to XML: " + filePath);
        } catch (Exception e) {
            System.err.println("Error saving contacts to XML.");
            e.printStackTrace();
        }
    }

    // Load from XML file
    public static ListOfContacts loadFromXML(String filePath) {
        ListOfContacts contacts = new ListOfContacts();
        try {
            File file = new File(filePath);
            if (file.exists()) {
                JAXBContext context = JAXBContext.newInstance(ListOfContacts.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                contacts = (ListOfContacts) unmarshaller.unmarshal(file);
                System.out.println("Contacts loaded from XML: " + filePath);
            } else {
                System.out.println("File does not exist: " + filePath);
            }
        } catch (Exception e) {
            System.err.println("Error loading contacts from XML.");
            e.printStackTrace();
        }
        return contacts;
    }
}
