package controller;

import com.google.inject.Inject;
import common.constants.InputFieldKeys;
import common.exceptions.ArgumentNullException;
import data.Delivery;
import data.Driver;
import data.Package;
import data.Vehicle;
import handlers.IMainActionHandler;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import logic.ILogic;
import models.MainViewDto;
import models.MainViewModel;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {
    private final Logger _log;
    private final ILogic _logic;
    private final IMainActionHandler _mainActionHandler;
    private final MainViewModel viewModel;

    /**
     * Creates new MainController instance
     * @param log logger
     * @param logic business logic
     * @throws ArgumentNullException params cannot be null
     */
    @Inject
    public MainController(Logger log, ILogic logic, IMainActionHandler mainActionHandler) {
        if ((_log = log) == null) throw new ArgumentNullException("log");
        if ((_logic = logic) == null) throw  new ArgumentNullException("logic");
        if ((_mainActionHandler = mainActionHandler) == null) throw  new ArgumentNullException("mainActionHandler");
        viewModel = new MainViewModel();
    }

    @FXML
    public void initialize() {
        //delivery table view
        deliveryIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        deliveryDriverNameCol.setCellValueFactory(new PropertyValueFactory<>("driver"));
        deliveryVehicleNumberCol.setCellValueFactory(new PropertyValueFactory<>("vehicle"));

        //driver table view
        driverIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        driverNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        //package selection table view
        packageIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        packageWeightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
        packageDestCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        packageContentCol.setCellValueFactory(new PropertyValueFactory<>("content"));
        packageSelectorCol.setCellValueFactory(new PropertyValueFactory<>("selected"));
        packageSelectionTable.setEditable(true);
        packageSelectorCol.setEditable(true);

        updateView();
    }

    public void handleDriverTabAction(ActionEvent actionEvent) {

        // preparing the view model to forward in dto
        viewModel.setSelectedDriver(driverTableView.getSelectionModel().getSelectedItem());
        viewModel.setDriverList(_logic.getAllDrivers());
        viewModel.getInputFieldValues().put(InputFieldKeys.DRIVER_NAME_INPUT_FIELD_KEY, driverNameInput.getText());

        // get database action
        var action = Arrays.stream(((Node)actionEvent.getSource()).getId().split("_")).toList().get(0);

        // sending data to handler
        var dto = new MainViewDto(viewModel, action);
        _log.log(Level.INFO, "MainViewDto created: " + dto);
        var response = _mainActionHandler.getDriverActionHandler().handleAction(dto);

        // fetching changes from database to display
        responseLabel.setText(response);
        updateView();
    }

    public void handleDeliveryTabAction(ActionEvent actionEvent) {
        //preparing the view model to forward in dto
        viewModel.setSelectedDriver(driverSelector.getSelectionModel().getSelectedItem());
        viewModel.setSelectedVehicle(vehicleSelector.getSelectionModel().getSelectedItem());

        // get database action
        var action =  Arrays.stream(((Node)actionEvent.getSource()).getId().split("_")).toList().get(0);

        //sending data to handler
        var dto = new MainViewDto(viewModel, action);
        _log.log(Level.INFO, "MainViewDto created: " + dto);
//        var response = _mainActionHandler.getDeliveryActionHandler().handleAction(dto);

        // fetching changes
//        responseLabel.setText(response);
        updateView();
    }

    private void updateView() {
        driverSelector.setItems(FXCollections.observableList(_logic.getAllDrivers()));
        vehicleSelector.setItems(FXCollections.observableList(_logic.getAllVehicles()));

        driverTableView.setItems(FXCollections.observableArrayList(_logic.getAllDrivers()));
        deliveryTable.setItems(FXCollections.observableList(_logic.getAllDeliveries()));
        packageSelectionTable.setItems(FXCollections.observableList(_logic.getAllPackages()));
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
    private Label responseLabel;
    @FXML
    private ChoiceBox<Driver> driverSelector;
    @FXML
    private TableView<Delivery> deliveryTable;
    @FXML
    private TableColumn<Delivery, Integer> deliveryIdCol;
    @FXML
    private TableColumn<Delivery, Driver> deliveryDriverNameCol;
    @FXML
    private TableColumn<Delivery, Vehicle> deliveryVehicleNumberCol;
    @FXML
    private TableView<Package> packageSelectionTable;
    @FXML
    private TableColumn<Package, Boolean> packageSelectorCol;
    @FXML
    private TableColumn<Package,Integer> packageIdCol;
    @FXML
    private TableColumn<Package, String> packageContentCol;
    @FXML
    private TableColumn<Package, Double> packageWeightCol;
    @FXML
    private TableColumn<Package, String> packageDestCol;
    @FXML
    private ChoiceBox<Vehicle> vehicleSelector;
}
