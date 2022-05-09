package data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PackageData")
public class Package {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "Content")
    private String content;
    @Column(name = "Destination")
    private String destination;
    @Column(name = "RegistrationTime")
    private Date registrationTime;
    @Column(name = "weight")
    private double weight;
    @Column(name = "inDelivery")
    private boolean inDelivery;

    public int getid() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getDestination() {
        return destination;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isInDelivery() {
        return inDelivery;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setInDelivery(boolean inDelivery) {
        this.inDelivery = inDelivery;
    }

    @Override
    public String toString() {
        return "Package{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", destination='" + destination + '\'' +
                ", registrationTime=" + registrationTime +
                ", weight=" + weight +
                ", inDelivery=" + inDelivery +
                '}';
    }
}