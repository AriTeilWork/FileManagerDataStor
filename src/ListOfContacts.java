import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    // Method to search for a person by name (case-insensitive)
    public void searchByName(String name) {
        if (name == null || name.isEmpty()) {
            System.out.println("Invalid name input.");
            return;
        }

        List<Person> results = contactList.stream()
                .filter(person -> person.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            System.out.println("No contact found with the name: " + name);
        } else {
            results.forEach(person -> System.out.println("Found: " + person));
        }
    }

    // Method to search for a person by email (case-insensitive)
    public void searchByEmail(String email) {
        if (email == null || email.isEmpty()) {
            System.out.println("Invalid email input.");
            return;
        }

        List<Person> results = contactList.stream()
                .filter(person -> person.getMail().equalsIgnoreCase(email))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            System.out.println("No contact found with the email: " + email);
        } else {
            results.forEach(person -> System.out.println("Found: " + person));
        }
    }

    // Method to search for a person by phone number (exact match)
    public void searchByPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            System.out.println("Invalid phone input.");
            return;
        }

        List<Person> results = contactList.stream()
                .filter(person -> person.getPhone().replaceAll("[^0-9]", "").equals(phone.replaceAll("[^0-9]", "")))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            System.out.println("No contact found with the phone number: " + phone);
        } else {
            results.forEach(person -> System.out.println("Found: " + person));
        }
    }
}
