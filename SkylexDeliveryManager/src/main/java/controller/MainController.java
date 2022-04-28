package controller;

import com.google.inject.Inject;
import common.exceptions.ArgumentNullException;
import data.Driver;
import handlers.DriverActionHandler;
import handlers.IDriverActionHandler;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import logic.ILogic;
import models.DriverTabDto;
import models.viewmodel.DriverViewModel;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {
    private final Logger _log;
    private final ILogic _logic;
    private final IDriverActionHandler _driverActionHandler;
    @FXML
    public ListView<Driver> driverListView;

    private final DriverViewModel _model;

    @FXML
    private TextField driverNameInput;

    /**
     * Creates new MainController instance
     * @param log logger
     * @param logic business logic
     * @throws ArgumentNullException params cannot be null
     */
    @Inject
    public MainController(Logger log, ILogic logic, IDriverActionHandler driverActionHandler) {
        if ((_log = log) == null) throw new ArgumentNullException("log");
        if ((_logic = logic) == null) throw  new ArgumentNullException("logic");
        if ((_driverActionHandler = driverActionHandler) == null) throw  new ArgumentNullException("driverActionHandler");
        _log.log(Level.INFO, "Hello from the MainController!");
        _model = new DriverViewModel();
        _model.setInputFieldValues(new HashMap<>());
        _model.setDriverList(_logic.getAllDrivers());
    }

    public void handleDriverTabAction(ActionEvent action) {

        _model.setSelectedDriver(driverListView.getSelectionModel().getSelectedItem());
        _model.setDriverList(_logic.getAllDrivers());
        _model.getInputFieldValues().put("DRIVER_NAME_KEY", driverNameInput.getText());

        var dto = new DriverTabDto(_model, ((Node)action.getSource()).getId());

        _driverActionHandler.handleAction(dto);

        driverListView.setItems(FXCollections.observableArrayList(_logic.getAllDrivers()));
    }
}
