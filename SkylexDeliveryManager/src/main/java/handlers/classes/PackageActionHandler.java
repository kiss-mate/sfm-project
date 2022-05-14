package handlers.classes;

import com.google.inject.Inject;
import common.constants.InputFieldKeys;
import common.exceptions.BusinessException;
import common.constants.ActionSource;
import handlers.interfaces.IPackageActionHandler;
import models.enums.ErrorCodes;
import logic.ILogic;
import models.MainViewDto;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PackageActionHandler implements IPackageActionHandler {
    private final ILogic _logic;
    private final Logger _log;

    @Inject
    public PackageActionHandler(Logger log, ILogic logic) {
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
        _logic.addPackage(
                dto.getMainViewModel().getInputFieldValues().get(InputFieldKeys.PACKAGE_CONTENT_INPUT_FIELD_KEY),
                dto.getMainViewModel().getInputFieldValues().get(InputFieldKeys.PACKAGE_DESTINATION_INPUT_FIELD_KEY),
                Double.parseDouble(dto.getMainViewModel().getInputFieldValues().get(InputFieldKeys.PACKAGE_WEIGHT_INPUT_FIELD_KEY))
                );
    }

    private void handleRemoveAction(MainViewDto dto) {
        if (dto.getMainViewModel().getSelectedPackage() == null)
            throw new BusinessException("No driver was selected to remove", ErrorCodes.NO_PACKAGE_SELECTED);

        _logic.deletePackage(dto.getMainViewModel().getSelectedPackage().getId());
    }

    private void handleUpdateAction(MainViewDto dto) {
        if (dto.getMainViewModel().getSelectedPackage() == null)
            throw new BusinessException("No package was selected to update", ErrorCodes.NO_PACKAGE_SELECTED);

        var delivery = dto.getMainViewModel().getSelectedPackage().getDelivery();

        _logic.changeOnePackage(
                dto.getMainViewModel().getSelectedPackage().getId(),
                dto.getMainViewModel().getInputFieldValues().get(InputFieldKeys.PACKAGE_CONTENT_INPUT_FIELD_KEY),
                dto.getMainViewModel().getInputFieldValues().get(InputFieldKeys.PACKAGE_DESTINATION_INPUT_FIELD_KEY),
                Double.parseDouble(dto.getMainViewModel().getInputFieldValues().get(InputFieldKeys.PACKAGE_WEIGHT_INPUT_FIELD_KEY)),
                delivery,
                dto.getMainViewModel().getSelectedPackage().isSelected());
    }

    private String toResponseString(BusinessException exception, String action) {
        String response;
        switch (exception.getErrorCode()) {
            case PACKAGE_NOT_FOUND -> response = "Sorry, cannot find this package!";
            case PACKAGE_CONTENT_EMPTY_OR_NULL -> response = "Sorry, you cannot save this as a package content!";
            case PACKAGE_INVALID_WEIGHT -> response = "Weight must be between 0.1 - 1000.0";
            case PACKAGE_DESTINATION_EMPTY_OR_NULL -> response = "Please set a properly formatted destination!";
            case NO_PACKAGE_SELECTED -> response = "Please select a package to perform the " + action + " action!";
            case UI_COMPLIANT -> response = exception.getMessage();
            default -> response = "Something went wrong";
        }

        return  response;
    }
}
