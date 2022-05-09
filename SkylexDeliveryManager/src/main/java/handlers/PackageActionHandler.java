package handlers;

import com.google.inject.Inject;
import common.constants.InputFieldKeys;
import common.exceptions.BusinessException;
import common.constants.ActionSource;
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
            return toResponseString(bex.getErrorCode(), dto.getActionSource());
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

        _logic.deletePackage(dto.getMainViewModel().getSelectedPackage().getid());
    }

    private void handleUpdateAction(MainViewDto dto) {
        if (dto.getMainViewModel().getSelectedPackage() == null)
            throw new BusinessException("No package was selected to update", ErrorCodes.NO_PACKAGE_SELECTED);

        var delivery = dto.getMainViewModel().getSelectedPackage().getDelivery();

        _logic.changeOnePackage(
                dto.getMainViewModel().getSelectedPackage().getid(),
                dto.getMainViewModel().getInputFieldValues().get(InputFieldKeys.PACKAGE_CONTENT_INPUT_FIELD_KEY),
                dto.getMainViewModel().getInputFieldValues().get(InputFieldKeys.PACKAGE_DESTINATION_INPUT_FIELD_KEY),
                Double.parseDouble(dto.getMainViewModel().getInputFieldValues().get(InputFieldKeys.PACKAGE_WEIGHT_INPUT_FIELD_KEY)),
                delivery,
                dto.getMainViewModel().getSelectedPackage().isSelected());
    }

    private String toResponseString(ErrorCodes errorCode, String action) {
        String response = "Something went wrong";
        switch (errorCode) {
            case PACKAGE_NOT_FOUND -> response = "Sorry, cannot find this package!";
            case PACKAGE_CONTENT_EMPTY_OR_NULL -> response = "Sorry, you cannot save this as a package content!";
            case NO_PACKAGE_SELECTED -> response = "Please select a package to perform the " + action + " action!";
            default -> {}
        }

        return  response;
    }
}
