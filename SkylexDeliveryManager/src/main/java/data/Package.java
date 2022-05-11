package data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.engine.internal.Cascade;

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
    @Column(name = "isSelected")
    private boolean selected;
    @Transient
    private BooleanProperty selectedProp = new SimpleBooleanProperty();

    // https://stackoverflow.com/questions/7869450/hibernate-and-h2-referential-integrity-constraint-violation-for-onetomany-bidi
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    public int getId() {
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

    public Delivery getDelivery() {
        return this.delivery;
    }

    public boolean isSelected() {
        return selected;
    }

    public BooleanProperty getSelectedProp() {
        return selectedProp;
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

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setSelectedProp(boolean selected) {
        selectedProp.setValue(selected);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    @Override
    public String toString() {
        return "Package{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", destination='" + destination + '\'' +
                ", registrationTime=" + registrationTime +
                ", weight=" + weight +
                ", inDelivery=" + selectedProp +
                '}';
    }
}