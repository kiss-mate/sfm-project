package data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.mapping.Set;

import javax.persistence.*;
import java.lang.annotation.ElementType;
import java.util.List;

@Entity
@Table(name = "DeliveryData")
public class Delivery {
    /**
     * Key for a Delivery entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private int id;
    /**
     * Name for a Delivery entity
     */
    @Column(name = "name")
    private String name;

    @OneToOne
    private Driver driver;

    @OneToOne
    private Vehicle vehicle;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "delivery")
    private List<Package> packages;

    @Column(name = "driverName")
    private String driverName;

    @Column(name = "vehiclePlateNumber")
    private String vehiclePlateNumber;

    public String getDriverName() {
        return driverName;
    }

    public String getVehiclePlateNumber() {
        return vehiclePlateNumber;
    }



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
        this.driverName = driver.getName();
    }

    public Driver getDriver(){
        return this.driver;
    }

    public void setVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
        this.vehiclePlateNumber = vehicle.getPlateNumber();
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
