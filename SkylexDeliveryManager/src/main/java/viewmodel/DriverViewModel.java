package viewmodel;

import data.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class DriverViewModel {
    private List<Driver> driverList = new ArrayList<>();
    private ObservableList<Driver> driversObsList = FXCollections.observableArrayList(driverList);

    public void setDriverList(List<Driver> driverList) {
        this.driverList = driverList;
    }

    public ObservableList<Driver> getDriversObsList(List<Driver> drivers) {
        this.driversObsList.setAll(drivers);
        return driversObsList;
    }
}
