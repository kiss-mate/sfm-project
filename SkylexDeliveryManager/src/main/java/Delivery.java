import java.util.List;

public class Delivery {
    public int Id;
    public int NumOfPackages;
    public String DriverName;
    public String VehiclePlateNumber;
    public List<Package> Packages;

    @Override
    public String toString() {
        return String.format("ID= %d\t DRIVER= %s\t VEHICLE= %s\t PACKAGES= %d", Id, DriverName, VehiclePlateNumber, NumOfPackages);
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getNumOfPackages() {
        return NumOfPackages;
    }

    public void setNumOfPackages(int numOfPackages) {
        NumOfPackages = numOfPackages;
    }

    public String getDriverName() {
        return DriverName;
    }

    public void setDriverName(String driverName) {
        DriverName = driverName;
    }

    public String getVehiclePlateNumber() {
        return VehiclePlateNumber;
    }

    public void setVehiclePlateNumber(String vehiclePlateNumber) {
        VehiclePlateNumber = vehiclePlateNumber;
    }

    public List<Package> getPackages() {
        return Packages;
    }

    public void setPackages(List<Package> packages) {
        Packages = packages;
    }
}