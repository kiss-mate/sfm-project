package models.viewmodel;

import data.Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DriverViewModel {
    private List<Driver> driverList = new ArrayList<>();
    private Driver selectedDriver;
    private Map<String,String> inputFieldValues;




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

    public Map<String, String> getInputFieldValues() {
        return inputFieldValues;
    }

    public void setInputFieldValues(Map<String, String> inputFieldValues) {
        this.inputFieldValues = inputFieldValues;
    }
}
