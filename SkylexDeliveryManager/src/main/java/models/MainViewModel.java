package models;

import data.Driver;
import data.Vehicle;
import data.Package;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainViewModel {
    private List<Driver> driverList;
    private Driver selectedDriver;
    private List<Vehicle> vehicleList;
    private Vehicle selectedVehicle;
    private List<Package> packageList;
    private Package selectedPackage;
    private Map<String, String> inputFieldValues;

    public MainViewModel() {
        vehicleList = new ArrayList<>();
        driverList = new ArrayList<>();
        inputFieldValues = new HashMap<>();
    }

    // region Awesome Setters, Getters

    // DRIVERS
    public List<Driver> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<Driver> driverList) {
        this.driverList = driverList;
    }

    public Driver getSelectedDriver() {
        return selectedDriver;
    }

    public void setSelectedDriver(Driver selectedDriver) {
        this.selectedDriver = selectedDriver;
    }


    // VEHICLES
    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public Vehicle getSelectedVehicle() {
        return selectedVehicle;
    }

    public void setSelectedVehicle(Vehicle selectedVehicle) {
        this.selectedVehicle = selectedVehicle;
    }


    // PACKAGES
    public List<Package> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<Package> packageList) {
        this.packageList = packageList;
    }

    public Package getSelectedPackage() {
        return selectedPackage;
    }

    public void setSelectedPackage(Package selectedPackage) {
        this.selectedPackage = selectedPackage;
    }



    public Map<String, String> getInputFieldValues() {
        return inputFieldValues;
    }

    public void setInputFieldValues(Map<String, String> inputFieldValues) {
        this.inputFieldValues = inputFieldValues;
    }

    // endregion


    @Override
    public String toString() {
        return "MainViewModel{" +
                "driverList=" + driverList +
                ", selectedDriver=" + selectedDriver +
                ", vehicleList=" + vehicleList +
                ", selectedVehicle=" + selectedVehicle +
                ", packageList=" + packageList +
                ", selectedPackage=" + selectedPackage +
                ", inputFieldValues=" + inputFieldValues +
                '}';
    }
}
