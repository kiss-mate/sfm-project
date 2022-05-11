package data;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

@Entity
@Table(name = "DriverData")
public class Driver  {
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

    @Column(name = "inDelivery")
    private boolean inDelivery;

    @Transient
    private BooleanProperty InDelivery = new SimpleBooleanProperty();
    public BooleanProperty getInDeliveryProp() {
        return InDelivery;
    }
    public void setInDeliveryProp(boolean value) {
        InDelivery.setValue(value);
    }

    public int getId() {
        return id;
    }

    /**
     * Gets the name of a driver
     */
    public String getName() {
        return name;
    }

    public boolean isInDelivery() {
        return inDelivery;
    }

    /**
     * Sets the name of a driver
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setInDelivery(boolean inDelivery) {
        this.inDelivery = inDelivery;
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
                ", inDelivery=" + inDelivery +
                '}';
    }
}
