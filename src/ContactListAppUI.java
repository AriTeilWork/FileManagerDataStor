import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContactListAppUI extends JFrame {

    private ListOfContacts contactListApp;

    public ContactListAppUI() {
        // Load contacts from JSON at the start
        contactListApp = JSONManager.loadFromJSON();

        setTitle("Contact List Manager");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // UI components
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
                try {
                    // Get data from fields
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String email = emailField.getText();
                    String phone = phoneField.getText();
                    String address = addressField.getText();

                    // Validate phone number
                    if (!isValidPhoneNumber(phone)) {
                        throw new IllegalArgumentException("Invalid phone number format.");
                    }

                    // Validate email
                    if (!isValidEmail(email)) {
                        throw new IllegalArgumentException("Invalid email format.");
                    }

                    // Add person to the list
                    Person newPerson = new Person(age, name, email, phone, address);
                    contactListApp.addPerson(newPerson);

                    // Save contacts after addition
                    try {
                        JSONManager.saveToJSON(contactListApp);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error saving to JSON: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    try {
                        XMLManager.saveToXML(contactListApp, "contacts.xml");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error saving to XML: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    try {
                        SQLiteManager.saveToSQLite(contactListApp);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error saving to SQLite: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }


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
        });

        // Show Contacts Button
        JButton showButton = new JButton("Show Contacts");
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        });

        // Add buttons to the bottom
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(addButton);
        bottomPanel.add(showButton);

        // Add panels to the frame
        add(panel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Phone number validation using regular expressions
    private boolean isValidPhoneNumber(String phone) {
        // Regex for validating phone numbers (supports formats like 123-456-7890, (123) 456-7890, etc.)
        String regex = "^(\\+\\d{1,3}[- ]?)?(\\(\\d{3}\\)[- ]?|\\d{3}[- ]?)\\d{3}[- ]?\\d{4}$";
        return phone.matches(regex);
    }

    // Email validation using regular expressions
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public static void main(String[] args) {
        new ContactListAppUI();
    }
}
