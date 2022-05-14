package handlers.classes;

import com.google.inject.Inject;
import common.constants.InputFieldKeys;
import common.exceptions.BusinessException;
import common.constants.ActionSource;
import handlers.interfaces.IDriverActionHandler;
import models.enums.ErrorCodes;
import logic.ILogic;
import models.MainViewDto;

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

    public String handleAction(MainViewDto dto) {
        try {
            switch (dto.getActionSource()) {
                case ActionSource.ADD -> handleAddAction(dto);
                case ActionSource.REMOVE -> handleRemoveAction(dto);
                case ActionSource.UPDATE -> handleUpdateAction(dto);
                default -> {
                    _log.log(Level.WARNING, "Cannot recognize action to handle");
                    return "Cannot perform this action";
                }
            }

            return dto.getActionSource() + " action was successful.";
        } catch (BusinessException bex) {
            _log.log(Level.WARNING, "Business exception occurred!", bex);
            return toResponseString(bex, dto.getActionSource());
        }
    }

    private void handleAddAction(MainViewDto dto) {
        _logic.addDriver(dto.getMainViewModel().getInputFieldValues().get(InputFieldKeys.DRIVER_NAME_INPUT_FIELD_KEY));
    }

    private void handleRemoveAction(MainViewDto dto) {
        if (dto.getMainViewModel().getSelectedDriver() == null)
            throw new BusinessException("No driver was selected to remove", ErrorCodes.NO_DIVER_SELECTED);
        _log.log(Level.INFO, "Driver to delete: " + dto.getMainViewModel().getSelectedDriver());
        _logic.deleteDriver(dto.getMainViewModel().getSelectedDriver().getId());
    }

    private void handleUpdateAction(MainViewDto dto) {
        if (dto.getMainViewModel().getSelectedDriver() == null)
            throw new BusinessException("No driver was selected to update", ErrorCodes.NO_DIVER_SELECTED);
        _log.log(Level.INFO, "Driver update: " + dto.getMainViewModel().getSelectedDriver());
        _logic.changeOneDriver(
                dto.getMainViewModel().getSelectedDriver().getId(),
                dto.getMainViewModel().getInputFieldValues().get(InputFieldKeys.DRIVER_NAME_INPUT_FIELD_KEY),
                dto.getMainViewModel().getSelectedDriver().isInDelivery());
    }

    private String toResponseString(BusinessException exception, String action) {
        String response;
        switch (exception.getErrorCode()) {
            case DRIVER_NOT_FOUND -> response = "Sorry, cannot find this driver!";
            case DRIVER_NAME_EMPTY_OR_NULL -> response = "Sorry, you cannot save this as a name for your driver!";
            case NO_DIVER_SELECTED -> response = "Please select a driver to perform the " + action + " action!";
            case UI_COMPLIANT -> response = exception.getMessage();
            default -> response = "Something went wrong";
        }

        return  response;
    }
}
