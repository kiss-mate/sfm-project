package data;

    import javax.persistence.*;
    import java.sql.*;
    import java.sql.Driver;
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

        @column(name = "driver")
        private Driver driver;

        @column(name = "vehicle")
        private Vehicle vehicle;

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


        public void setDriver(Driver driver){
            this.driver =  driver;

        }

        public Driver getDriver(){
            return this.driver;
        }

        public void setVehicle(Vehicle vehicle){
            this.vehicle = vehicle;
        }
        public Vehicle getVehicle(){
           retrun this.vehicle;
        }




        /**
         * Converts the Delivery object to a readable string
         * @return string representation of a Driver object
         */
        @Override
        public String toString() {
            return "Delivery{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", Driver: " + driver.getName() + '\'' +
                    ", Vehicle: " + vehicle.getName() + '\'' +
                    ", "
                    '}';
        }
    }

