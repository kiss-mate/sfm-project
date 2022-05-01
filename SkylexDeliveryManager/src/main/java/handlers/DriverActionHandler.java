package handlers;

import com.google.inject.Inject;
import common.constants.InputFieldKeys;
import common.exceptions.BusinessException;
import common.constants.ActionSource;
import enums.ErrorCodes;
import logic.ILogic;
import models.DriverTabDto;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DriverActionHandler implements IDriverActionHandler {
    private final ILogic _logic;
    private final Logger _log;

    @Inject
    public DriverActionHandler(Logger log, ILogic logic) {
        _log = log;
        _logic = logic;
    }

    public String handleAction(DriverTabDto dto) {
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
            return toResponseString(bex.getErrorCode(), dto.getActionSource());
        }
    }

    private void handleAddAction(DriverTabDto dto) {
        _logic.addDriver(dto.getDriverViewModel().getInputFieldValues().get(InputFieldKeys.DRIVER_NAME_INPUT_FIELD_KEY));
    }

    private void handleRemoveAction(DriverTabDto dto) {
        if (dto.getDriverViewModel().getSelectedDriver() == null)
            throw new BusinessException("No driver was selected to remove", ErrorCodes.NO_DIVER_SELECTED);

        _logic.deleteDriver(dto.getDriverViewModel().getSelectedDriver().getId());
    }

    private void handleUpdateAction(DriverTabDto dto) {
        if (dto.getDriverViewModel().getSelectedDriver() == null)
            throw new BusinessException("No driver was selected to update", ErrorCodes.NO_DIVER_SELECTED);

        _logic.changeOneDriver(
                dto.getDriverViewModel().getSelectedDriver().getId(),
                dto.getDriverViewModel().getInputFieldValues().get(InputFieldKeys.DRIVER_NAME_INPUT_FIELD_KEY));
    }

    private String toResponseString(ErrorCodes errorCode, String action) {
        String response = "Something went wrong";
        switch (errorCode) {
            case DRIVER_NOT_FOUND -> response = "Sorry, cannot find this driver!";
            case DRIVER_NAME_EMPTY_OR_NULL -> response = "Sorry, you cannot save this as a name for your driver!";
            case NO_DIVER_SELECTED -> response = "Please select a driver to perform the " + action + " action!";
            default -> {}
        }

        return  response;
    }
}
