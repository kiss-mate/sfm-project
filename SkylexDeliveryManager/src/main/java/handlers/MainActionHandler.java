package handlers;

import com.google.inject.Inject;

public class MainActionHandler implements IMainActionHandler{
    private final IDriverActionHandler _driverActionHandler;
    private final IVehicleActionHandler _vehicleActionHandler;

    @Inject
    public MainActionHandler(IDriverActionHandler driverActionHandler, IVehicleActionHandler vehicleActionHandler) {
        _driverActionHandler = driverActionHandler;
        _vehicleActionHandler = vehicleActionHandler;
    }

    public IDriverActionHandler getDriverActionHandler() {
        return _driverActionHandler;
    }

    public IVehicleActionHandler getVehicleActionHandler() {
        return _vehicleActionHandler;
    }
}
