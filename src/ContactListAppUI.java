import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;  // Add this for ArrayList
import java.util.List;       // Add this for List
import java.util.stream.Collectors;

public class ContactListAppUI extends JFrame {

    private ListOfContacts contactListApp;

    public ContactListAppUI() {
        contactListApp = JSONManager.loadFromJSON();

        setTitle("Contact List Manager");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Main Input Panel for Adding Contacts
        JPanel panel = new JPanel(new GridLayout(5, 2));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField();

        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField();

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(addressLabel);
        panel.add(addressField);

        // Add Contact Button
        JButton addButton = new JButton("Add Contact");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addContact(nameField, ageField, emailField, phoneField, addressField);
            }
        });

        // Show Contacts Button
        JButton showButton = new JButton("Show Contacts");
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showContacts();
            }
        });

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        JLabel searchLabel = new JLabel("Search by:");
        JTextField searchField = new JTextField(10);
        String[] searchOptions = {"Name", "Email", "Phone"};
        JComboBox<String> searchCombo = new JComboBox<>(searchOptions);
        JButton searchButton = new JButton("Search");

        searchPanel.add(searchLabel);
        searchPanel.add(searchCombo);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch(searchField.getText(), (String) searchCombo.getSelectedItem());
            }
        });

        // Bottom Panel to hold buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(addButton);
        bottomPanel.add(showButton);

        // Add panels to the frame
        add(panel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        add(searchPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    // Method to add a contact
    private void addContact(JTextField nameField, JTextField ageField, JTextField emailField, JTextField phoneField, JTextField addressField) {
        try {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String email = emailField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();

            if (!isValidPhoneNumber(phone)) {
                throw new IllegalArgumentException("Invalid phone number format.");
            }

            if (!isValidEmail(email)) {
                throw new IllegalArgumentException("Invalid email format.");
            }

            Person newPerson = new Person(age, name, email, phone, address);
            contactListApp.addPerson(newPerson);
            JSONManager.saveToJSON(contactListApp);

            nameField.setText("");
            ageField.setText("");
            emailField.setText("");
            phoneField.setText("");
            addressField.setText("");

            JOptionPane.showMessageDialog(null, "Contact added successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Age must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // Method to show all contacts
    private void showContacts() {
        if (contactListApp.getContactList().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No contacts found.");
        } else {
            JTextArea textArea = new JTextArea(15, 30);
            for (Person person : contactListApp.getContactList()) {
                textArea.append(person.toString() + "\n");
            }
            JScrollPane scrollPane = new JScrollPane(textArea);
            JOptionPane.showMessageDialog(null, scrollPane, "Contacts", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Method to perform search
    private void performSearch(String query, String searchBy) {
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a search term.");
            return;
        }

        List<Person> results = new ArrayList<>();
        switch (searchBy) {
            case "Name":
                results = contactListApp.getContactList().stream()
                        .filter(person -> person.getName().equalsIgnoreCase(query))
                        .collect(Collectors.toList());
                break;
            case "Email":
                results = contactListApp.getContactList().stream()
                        .filter(person -> person.getMail().equalsIgnoreCase(query))
                        .collect(Collectors.toList());
                break;
            case "Phone":
                results = contactListApp.getContactList().stream()
                        .filter(person -> person.getPhone().replaceAll("[^0-9]", "").equals(query.replaceAll("[^0-9]", "")))
                        .collect(Collectors.toList());
                break;
        }

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No contact found with the " + searchBy.toLowerCase() + ": " + query);
        } else {
            JTextArea textArea = new JTextArea(10, 30);
            results.forEach(person -> textArea.append(person.toString() + "\n"));
            JScrollPane scrollPane = new JScrollPane(textArea);
            JOptionPane.showMessageDialog(null, scrollPane, "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Validation methods
    private boolean isValidPhoneNumber(String phone) {
        String regex = "^(\\+\\d{1,3}[- ]?)?(\\(\\d{3}\\)[- ]?|\\d{3}[- ]?)\\d{3}[- ]?\\d{4}$";
        return phone.matches(regex);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public static void main(String[] args) {
        new ContactListAppUI();
    }
}
