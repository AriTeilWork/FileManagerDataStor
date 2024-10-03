import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {
    private int age;
    private String name;
    private String mail;
    private String phone;
    private String address;

    // Empty constructor needed for JAXB
    public Person() {}

    public Person(int age, String name, String mail, String phone, String address) {
        this.age = age;
        this.name = name;
        this.mail = mail;
        this.phone = phone;
        this.address = address;
    }

    @XmlElement
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @XmlElement
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @XmlElement
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + ", mail=" + mail + ", phone=" + phone + ", address=" + address + "]";
    }
}
