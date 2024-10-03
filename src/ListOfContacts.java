import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class ListOfContacts {

    // List to store Person objects
    private List<Person> contactList;

    // Constructor to initialize the contact list
    public ListOfContacts() {
        contactList = new ArrayList<>();
    }

    @XmlElement
    public List<Person> getContactList() {
        return contactList;
    }

    public void setContactList(List<Person> contactList) {
        this.contactList = contactList;
    }

    // Method to add a person to the contact list
    public void addPerson(Person person) {
        contactList.add(person);
    }

    // Method to print all people in the contact list
    public void printContactList() {
        if (contactList.isEmpty()) {
            System.out.println("Contact list is empty.");
        } else {
            for (Person person : contactList) {
                System.out.println(person);
            }
        }
    }

    // Method to search for a person by name
    public void searchByName(String name) {
        boolean found = false;
        for (Person person : contactList) {
            if (person.getName().equalsIgnoreCase(name)) {
                System.out.println("Found: " + person);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No contact found with the name: " + name);
        }
    }

    // Method to search for a person by email
    public void searchByEmail(String email) {
        boolean found = false;
        for (Person person : contactList) {
            if (person.getMail().equalsIgnoreCase(email)) {
                System.out.println("Found: " + person);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No contact found with the email: " + email);
        }
    }

    // Method to search for a person by phone number
    public void searchByPhone(String phone) {
        boolean found = false;
        for (Person person : contactList) {
            if (person.getPhone().equals(phone)) {
                System.out.println("Found: " + person);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No contact found with the phone number: " + phone);
        }
    }
}
