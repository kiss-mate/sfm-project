package handlers;

import com.google.inject.Inject;
import handlers.interfaces.*;

public class MainActionHandler implements IMainActionHandler {
    private final IDriverActionHandler _driverActionHandler;
    private final IVehicleActionHandler _vehicleActionHandler;
    private final IDeliveryActionHandler _deliveryActionHandler;
    private final IPackageActionHandler _packageActionHandler;

    @Inject
    public MainActionHandler(IDriverActionHandler driverActionHandler,
                             IVehicleActionHandler vehicleActionHandler,
                             IDeliveryActionHandler deliveryActionHandler,
                             IPackageActionHandler packageActionHandler) {
        _driverActionHandler = driverActionHandler;
        _vehicleActionHandler = vehicleActionHandler;
        _deliveryActionHandler = deliveryActionHandler;
        _packageActionHandler = packageActionHandler;
    }

    public IDriverActionHandler getDriverActionHandler() {
        return _driverActionHandler;
    }

    public IVehicleActionHandler getVehicleActionHandler() {
        return _vehicleActionHandler;
    }

    public IDeliveryActionHandler getDeliveryActionHandler() {
        return _deliveryActionHandler;
    }

    public IPackageActionHandler getPackageActionHandler() {
        return _packageActionHandler;
    }
}
