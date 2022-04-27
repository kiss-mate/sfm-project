package controller;

import com.google.inject.Inject;
import common.exceptions.ArgumentNullException;
import data.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import logic.ILogic;
import viewmodel.DriverViewModel;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {
    private final Logger _log;
    private final ILogic _logic;
    @FXML
    public ListView<Driver> driverListView;

    private final DriverViewModel _model;
    @FXML
    public Button removeButtin;

    @FXML
    private TextField driverNameInput;

    /**
     * Creates new MainController instance
     * @param log logger
     * @param logic business logic
     * @throws ArgumentNullException params cannot be null
     */
    @Inject
    public MainController(Logger log, ILogic logic) {
        if ((_log = log) == null) throw new ArgumentNullException("log");
        if ((_logic = logic) == null) throw  new ArgumentNullException("logic");
        _log.log(Level.INFO, "Hello from the MainController!");
        _model = new DriverViewModel();
        _model.setDriverList(_logic.getAllDrivers());
    }

    public void handleSubmitAction(ActionEvent actionEvent) {
        _log.log(Level.INFO, "Submit button clicked");
        try {
            var driver = _logic.addDriver(driverNameInput.getText());

            driverListView.setItems(_model.getDriversObsList(_logic.getAllDrivers()));

            _log.log(Level.INFO, "Submit action handled");
        } catch (RuntimeException ex) {
            _log.log(Level.SEVERE, "Error while handling submit action", ex);
        }
    }

    public void handleRemoveAction(ActionEvent actionEvent) {
        _logic.deleteDriver(driverListView.getSelectionModel().getSelectedItem().getId());
        driverListView.setItems(_model.getDriversObsList(_logic.getAllDrivers()));
    }
}
