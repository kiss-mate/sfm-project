package handlers;

import com.google.inject.Inject;
import data.Driver;
import enums.ActionSource;
import logic.ILogic;
import logic.Logic;
import models.DriverTabDto;

import java.util.logging.Logger;

public class DriverActionHandler implements IDriverActionHandler {
    private final ILogic _logic;
    private final Logger _log;

    @Inject
    public DriverActionHandler(Logger log, ILogic logic) {
        _log = log;
        _logic = logic;
    }

    public void handleAction(DriverTabDto dto) {
        switch (dto.getActionSource()) {
            case ActionSource.ADD -> handleAddAction(dto);
            case ActionSource.REMOVE -> handleRemoveAction(dto);
            default -> {}
        }
    }

    //TODO try catch logic
    private void handleAddAction(DriverTabDto dto) {
        _logic.addDriver(dto.getDriverViewModel().getInputFieldValues().get("DRIVER_NAME_KEY"));
    }

    private void handleRemoveAction(DriverTabDto dto) {
        _logic.deleteDriver(dto.getDriverViewModel().getSelectedDriver().getId());
    }
}
