package controller;

import com.google.inject.Inject;
import common.constants.InputFieldKeys;
import common.exceptions.ArgumentNullException;
import data.Driver;
import handlers.IDriverActionHandler;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import logic.ILogic;
import models.DriverTabDto;
import models.viewmodel.DriverViewModel;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {
    private final Logger _log;
    private final ILogic _logic;
    private final DriverViewModel _model;
    private final IDriverActionHandler _driverActionHandler;


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

    @FXML
    public void initialize() {
        updateView();
    }

    public void handleDriverTabAction(ActionEvent actionEvent) {

        // preparing the view model to forward in dto
        _model.setSelectedDriver(driverTableView.getSelectionModel().getSelectedItem());
        _model.setDriverList(_logic.getAllDrivers());
        _model.getInputFieldValues().put(InputFieldKeys.DRIVER_NAME_INPUT_FIELD_KEY, driverNameInput.getText());

        // get database action
        var action = ((Node)actionEvent.getSource()).getId();

        // sending data to handler
        var dto = new DriverTabDto(_model, action);
        _log.log(Level.INFO, "DriverTabDto created: ", dto);
        var response = _driverActionHandler.handleAction(dto);

        // fetching changes from database to display
        responseLabel.setText(response);
        updateView();
    }

    private void updateView() {
        driverIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        driverNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        driverTableView.setItems(FXCollections.observableArrayList(_logic.getAllDrivers()));
    }

    // FXML fields
    @FXML
    private TableView<Driver> driverTableView;
    @FXML
    private TableColumn<Driver,Integer> driverIdCol;
    @FXML
    private TableColumn<Driver, String> driverNameCol;
    @FXML
    private TextField driverNameInput;
    @FXML
    private AnchorPane driverTabArea;
    @FXML
    private Label responseLabel;

}
