package handlers;

import com.google.inject.Inject;
import common.constants.ActionSource;
import common.constants.InputFieldKeys;
import common.exceptions.BusinessException;
import logic.ILogic;
import models.MainViewDto;
import models.enums.ErrorCodes;

import java.util.logging.Level;
import java.util.logging.Logger;

public class VehicleActionHandler implements IVehicleActionHandler {
    private final ILogic _logic;
    private final Logger _log;

    @Inject
    public VehicleActionHandler(Logger log, ILogic logic) {
        _log = log;
        _logic = logic;
    }

    public String handleAction(MainViewDto dto) {
        try {
            switch (dto.getActionSource()) {
                case ActionSource.ADD -> handleAddAction(dto);
                case ActionSource.REMOVE -> handleRemoveAction(dto);
                case ActionSource.UPDATE -> handleUpdateAction(dto);
                default -> _log.log(Level.WARNING, "Cannot recognize action to handle");
            }

            return dto.getActionSource() + " action was successful.";
        } catch (BusinessException bex) {
            _log.log(Level.WARNING, "Business exception occurred: " + bex.getMessage());
            return toResponseString(bex, dto.getActionSource());
        }
    }

    private void handleAddAction(MainViewDto dto) {
        _logic.addVehicle(
                dto.getMainViewModel().getInputFieldValues().get(InputFieldKeys.VEHICLE_PLATE_NUMBER_INPUT_FIELD_KEY),
                Double.parseDouble(dto.getMainViewModel().getInputFieldValues().get(InputFieldKeys.VEHICLE_MAX_CAPACITY_INPUT_FIELD_KEY))
        );
    }

    private void handleRemoveAction(MainViewDto dto) {
        if (dto.getMainViewModel().getSelectedVehicle() == null)
            throw new BusinessException("No vehicle was selected to remove", ErrorCodes.NO_VEHICLE_SELECTED);
        _log.log(Level.INFO, "Vehicle to remove: " + dto.getMainViewModel().getSelectedVehicle().getInDeliveryProp());
        _logic.deleteVehicle(dto.getMainViewModel().getSelectedVehicle().getId());
    }

    private void handleUpdateAction(MainViewDto dto) {
        if (dto.getMainViewModel().getSelectedVehicle() == null)
            throw new BusinessException("No driver was selected to update", ErrorCodes.NO_VEHICLE_SELECTED);

        _logic.changeOneVehicle(
                dto.getMainViewModel().getSelectedVehicle().getId(),
                dto.getMainViewModel().getInputFieldValues().get(InputFieldKeys.VEHICLE_PLATE_NUMBER_INPUT_FIELD_KEY),
                Double.parseDouble(dto.getMainViewModel().getInputFieldValues().get(InputFieldKeys.VEHICLE_MAX_CAPACITY_INPUT_FIELD_KEY)),
                dto.getMainViewModel().getSelectedVehicle().getCurrentLoad(),
                dto.getMainViewModel().getSelectedVehicle().isInDelivery());
    }

    private String toResponseString(BusinessException exception, String action) {
        String response;
        switch (exception.getErrorCode()) {
            case VEHICLE_NOT_FOUND -> response = "Sorry, cannot find this vehicle!";
            case VEHICLE_MAX_CAPACITY_INVALID -> response = "Sorry, you cannot save this as a max capacity for your vehicle!";
            case VEHICLE_PLATE_NUMBER_INVALID -> response = "Sorry, you cannot save this as a plate number for your vehicle!";
            case NO_VEHICLE_SELECTED -> response = "Please select a vehicle to perform the " + action + " action!";
            case UI_COMPLIANT -> response = exception.getMessage();
            default -> response = "Something went wrong";
        }

        return  response;
    }
}
