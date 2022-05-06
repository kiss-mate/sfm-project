package models;

import models.viewmodel.DriverViewModel;

public class DriverTabDto {
    private DriverViewModel driverViewModel;
    private String actionSource;

    public DriverTabDto(DriverViewModel driverViewModel, String actionSource) {
        this.driverViewModel = driverViewModel;
        this.actionSource = actionSource;
    }

    public DriverViewModel getDriverViewModel() {
        return driverViewModel;
    }

    public String getActionSource() {
        return actionSource;
    }
}
