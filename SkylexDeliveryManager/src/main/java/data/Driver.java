package data;


import javax.persistence.*;

@Entity
@Table(name = "DriverData")
public class Driver {
    /**
     * Key for a Driver entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * Name for a Driver entity
     */
    @Column(name = "name")
    private String name;

    public int getId() {
        return id;
    }

    /**
     * Gets the name of a driver
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of a driver
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Converts the Driver object to a readable string
     * @return string representation of a Driver object
     */
    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
