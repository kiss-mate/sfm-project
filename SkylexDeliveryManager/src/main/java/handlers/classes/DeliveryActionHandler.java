package handlers.classes;

import com.google.inject.Inject;
import common.constants.ActionSource;
import common.exceptions.BusinessException;
import data.Delivery;
import data.Package;
import handlers.interfaces.IDeliveryActionHandler;
import logic.ILogic;
import models.MainViewDto;
import models.enums.ErrorCodes;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeliveryActionHandler implements IDeliveryActionHandler {
    private final ILogic _logic;
    private final Logger _log;

    @Inject
    public DeliveryActionHandler(Logger log, ILogic logic) {
        _log = log;
        _logic = logic;
    }

    public String handleAction(MainViewDto dto) {
        try {
            switch (dto.getActionSource()) {
                case ActionSource.ADD : handleAddAction(dto); break;
                case ActionSource.REMOVE : handleRemoveAction(dto); break;
                case ActionSource.UPDATE : handleUpdateAction(dto); break;
                default : _log.log(Level.WARNING, "Cannot recognize action to handle"); break;
            }

            return dto.getActionSource() + " action was successful.";
        } catch (BusinessException bex) {
            _log.log(Level.WARNING, "Business exception occurred: " + bex.getMessage());
            return toResponseString(bex, dto.getActionSource());
        }
    }

    private void handleAddAction(MainViewDto dto) {
        var delivery = _logic.addDelivery(
                dto.getMainViewModel().getSelectedDriver(),
                dto.getMainViewModel().getSelectedVehicle()
        );

        updatePackages(dto.getMainViewModel().getPackageList(), delivery);
    }

    private void handleRemoveAction(MainViewDto dto) {
        if (dto.getMainViewModel().getSelectedDelivery() == null)
            throw new BusinessException("No delivery was selected to remove", ErrorCodes.NO_DELIVERY_SELECTED);

        _logic.deleteDelivery(dto.getMainViewModel().getSelectedDelivery().getId());
    }

    private void handleUpdateAction(MainViewDto dto) {
        if (dto.getMainViewModel().getSelectedDelivery() == null)
            throw new BusinessException("No delivery was selected to remove", ErrorCodes.NO_DELIVERY_SELECTED);

        _logic.changeOneDelivery(
                dto.getMainViewModel().getSelectedDelivery().getId(),
                dto.getMainViewModel().getSelectedDriver(),
                dto.getMainViewModel().getSelectedVehicle()
        );

        updatePackages(dto.getMainViewModel().getPackageList(), dto.getMainViewModel().getSelectedDelivery());
    }

    private String toResponseString(BusinessException exception, String action) {
        String response;
        switch (exception.getErrorCode()) {
            case NO_DELIVERY_SELECTED : response = "Please select a delivery to perform the " + action + " action!"; break;
            case DELIVERY_NOT_FOUND : response = "Cannot find requested delivery"; break;
            case NO_DIVER_SELECTED : response = "Please select driver for the delivery!"; break;
            case NO_VEHICLE_SELECTED : response = "Please select vehicle for the delivery!"; break;
            case UI_COMPLIANT : response = exception.getMessage(); break;
            default : response = "Something went wrong"; break;
        }

        return  response;
    }

    private void updatePackages(List<Package> packages, Delivery delivery) {
        for ( var p : packages) {
            _log.log(Level.INFO, ""+p.getSelectedProp().get());
            _logic.changeOnePackage(p.getId(),p.getContent(),p.getDestination(), p.getWeight(), delivery, p.getSelectedProp().get());
        }
    }
}