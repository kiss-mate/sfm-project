package data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javax.persistence.*;

@Entity
@Table(name = "VehicleData")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "plateNumber")
    private String plateNumber;

    @Column(name = "inDelivery")
    private boolean inDelivery;

    @Column(name = "maxCapacity")
    private double maxCapacity;

    @Column(name = "currentLoad")
    private double currentLoad;

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

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public boolean isInDelivery() {
        return inDelivery;
    }

    public void setInDelivery(boolean inDelivery) {
        this.inDelivery = inDelivery;
    }

    public double getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(double maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public double getCurrentLoad() {
        return currentLoad;
    }

    public void setCurrentLoad(double currentLoad) {
        this.currentLoad = currentLoad;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", plateNumber='" + plateNumber + '\'' +
                ", inDelivery=" + inDelivery +
                ", maxCapacity=" + maxCapacity +
                ", currentLoad=" + currentLoad +
                '}';
    }
}
