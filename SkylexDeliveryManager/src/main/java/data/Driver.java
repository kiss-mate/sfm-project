package data;

import javax.persistence.*;

@Entity
@Table(name = "DriverData")
public class Driver {
    /**
     * Key for a Sample entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * String value for a Sample entity
     */
    @Column(name = "name")
    private String name;

    public int getId() {
        return id;
    }

    /**
     * Gets the value1 of a Sample object
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the value2 of a Sample object
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Converts the Driver object to a readable string
     * @return
     */
    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
