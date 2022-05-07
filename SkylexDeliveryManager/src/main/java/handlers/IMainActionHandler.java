package handlers;

public interface IMainActionHandler {
    IDriverActionHandler getDriverActionHandler();
    IVehicleActionHandler getVehicleActionHandler();
}
