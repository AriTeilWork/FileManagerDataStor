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
                // Get data from fields
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String email = emailField.getText();
                String phone = phoneField.getText();
                String address = addressField.getText();

                // Add person to the list
                Person newPerson = new Person(age, name, email, phone, address);
                contactListApp.addPerson(newPerson);

                // Save contacts after addition
                JSONManager.saveToJSON(contactListApp);

                // Clear fields
                nameField.setText("");
                ageField.setText("");
                emailField.setText("");
                phoneField.setText("");
                addressField.setText("");

                JOptionPane.showMessageDialog(null, "Contact added successfully!");
            }
        });

        // Show Contacts Button
        JButton showButton = new JButton("Show Contacts");
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display contacts in a message dialog
                contactListApp.printContactList();
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

    public static void main(String[] args) {
        new ContactListAppUI();
    }
}
