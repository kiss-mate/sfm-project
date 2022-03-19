package data;

import javax.persistence.*;

@Entity
@Table(name = "SampleData")
public class Sample {
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
    @Column(name = "value1")
    private String value1;

    /**
     * Int value for a Sample entity
     */
    @Column(name = "value2")
    private int value2;

    /**
     * Gets the id of a Sample object
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the value1 of a Sample object
     */
    public String getValue1() {
        return value1;
    }

    /**
     * Gets the value2 of a Sample object
     */
    public int getValue2() {
        return value2;
    }

    /**
     * Sets the value1 of a Sample object
     * @param value1 string value
     */
    public void setValue1(String value1) {
        this.value1 = value1;
    }

    /**
     * Sets the value2 of a Sample object
     * @param value2 int value
     */
    public void setValue2(int value2) {
        this.value2 = value2;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "id=" + id +
                ", value1='" + value1 + '\'' +
                ", value2=" + value2 +
                '}';
    }
}
