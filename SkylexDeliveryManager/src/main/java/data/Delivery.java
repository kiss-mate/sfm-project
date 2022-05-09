package data;

import javax.persistence.*;
import java.sql.*;
import java.sql.Driver;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

@Entity
@Table(name = "DeliveryData")
public class Delivery {
    /**
     * Key for a Delivery entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    /**
     * Name for a Delivery entity
     */
    @Column(name = "name")
    private String name;

    @Column(name = "driver")
    private Driver driver;

    @Column(name = "vehicle")
    private Vehicle vehicle;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "packages")
    private List<Package> packages;

    public int getId() {
        return id;
    }

    /**
     * Gets the name of a delivery
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of a delivery
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setDriver(Driver driver) {
        this.driver =  driver;
    }

    public Driver getDriver(){
        return this.driver;
    }

    public void setVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle(){
       return this.vehicle;
    }

    public void setPackages(List<Package> packages) {
    this.packages = packages;
    }

    public List<Package> getPackages(){
        return this.packages;
    }

    /**
     * Converts the Delivery object to a readable string
     * @return string representation of a Delivery object
     */
    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", Driver= " + driver + '\'' +
                ", Vehicle= " + vehicle + '\'' +
                ", Packages=" + packages +
                '}';
    }
}
