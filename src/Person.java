import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {
    private int age;
    private String name;
    private String mail;
    private String phone;
    private String address;

    // Default constructor (required for JAXB and JSON)
    public Person() {}

    // Parameterized constructor for easy instantiation
    public Person(int age, String name, String mail, String phone, String address) {
        setAge(age);
        setName(name);
        setMail(mail);
        setPhone(phone);
        setAddress(address);
    }

    @XmlElement
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative.");
        }
        this.age = age;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        this.name = name;
    }

    @XmlElement
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        if (!mail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.mail = mail;
    }

    @XmlElement
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        // A basic check for 10-digit format or XXX-XXX-XXXX format
        if (!phone.matches("\\d{10}|(?:\\d{3}-){2}\\d{4}")) {
            throw new IllegalArgumentException("Invalid phone number format.");
        }
        this.phone = phone;
    }

    @XmlElement
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty.");
        }
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + ", mail=" + mail + ", phone=" + phone + ", address=" + address + "]";
    }
}
