package main.java.data;
import javax.persistence.*;
import java.util.Date;

@entity
@table(name = "PackageData")
public class Package {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @column(name = "Content")
    private String Content;
    @column(name = "Destination")
    private String Destination;
    @column(name = "RegistrationTime")
    private Date RegistrationTime;
    @column(name = "weight")
    private double weight;
    @column(name = "inDelivery")
    private boolean inDelivery;

    public int getid() {
        return id;
    }

    public String getContent() {
        return Content;
    }

    public String getDestination() {
        return Destination;
    }

    public Date getRegistrationTime() {
        return RegistrationTime;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isInDelivery() {
        return inDelivery;
    }



    public void setid(int id) {
        this.ID = id;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public void setRegistrationTime(Date registrationTime) {
        RegistrationTime = registrationTime;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setInDelivery(boolean inDelivery) {
        this.inDelivery = inDelivery;
    }

    @Override
    public String toString(){
        return "Package{" + Integer.toString(id) + ", "
                + Content + ", " + Destination + ", "
                +RegistrationTime + ", " + Double.toString(weight)
                + ", " + inDelivery + '}'
    }
}