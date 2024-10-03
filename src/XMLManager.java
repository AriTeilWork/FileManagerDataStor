import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XMLManager {
    public static void saveToXML(ListOfContacts contactListApp) {
        try {
            JAXBContext context = JAXBContext.newInstance(ListOfContacts.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            File file = new File("contacts.xml");
            marshaller.marshal(contactListApp, file);
            System.out.println("Contacts saved to XML.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ListOfContacts loadFromXML() {
        ListOfContacts contacts = new ListOfContacts();
        try {
            File file = new File("contacts.xml");
            if (file.exists()) {
                JAXBContext context = JAXBContext.newInstance(ListOfContacts.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                contacts = (ListOfContacts) unmarshaller.unmarshal(file);
                System.out.println("Contacts loaded from XML.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contacts;
    }
}
