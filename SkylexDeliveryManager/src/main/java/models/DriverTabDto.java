package models;

import models.viewmodel.DriverViewModel;

public class DriverTabDto {
    DriverViewModel driverViewModel;
    String actionSource;

    public DriverTabDto(DriverViewModel driverViewModel, String actionSource) {
        this.driverViewModel = driverViewModel;
        this.actionSource = actionSource;
    }

    public DriverViewModel getDriverViewModel() {
        return driverViewModel;
    }

    public void setDriverViewModel(DriverViewModel driverViewModel) {
        this.driverViewModel = driverViewModel;
    }

    public String getActionSource() {
        return actionSource;
    }

    public void setActionSource(String actionSource) {
        this.actionSource = actionSource;
    }
}
