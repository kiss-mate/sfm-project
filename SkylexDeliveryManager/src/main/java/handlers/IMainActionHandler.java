package handlers;

import handlers.interfaces.IDeliveryActionHandler;
import handlers.interfaces.IDriverActionHandler;
import handlers.interfaces.IPackageActionHandler;
import handlers.interfaces.IVehicleActionHandler;

public interface IMainActionHandler {
    IDriverActionHandler getDriverActionHandler();
    IVehicleActionHandler getVehicleActionHandler();
    IDeliveryActionHandler getDeliveryActionHandler();
    IPackageActionHandler getPackageActionHandler();
}
