package controller;

import com.google.inject.Inject;
import common.constants.ActionSource;
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
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import logic.ILogic;
import models.MainViewDto;
import models.MainViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
        packageWeightInput.setEditable(true);
        packageWeightInput.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1, 1000));
        vehicleMaxCapInput.setEditable(true);
        vehicleMaxCapInput.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(100,15000));

        //delivery table view
        deliveryIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        deliveryDriverNameCol.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        deliveryVehicleNumberCol.setCellValueFactory(new PropertyValueFactory<>("vehiclePlateNumber"));

        //driver table view
        driverIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        driverNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        // vehicle table view
        vehicleCapCol.setCellValueFactory(new PropertyValueFactory<>("maxCapacity"));
        vehiclePlateCol.setCellValueFactory(new PropertyValueFactory<>("plateNumber"));

        // package table view
        packageContentCol.setCellValueFactory(new PropertyValueFactory<>("content"));
        packageDestCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        packageWeightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));

        //package selection table view
        deliveryPackageIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        deliveryPackageWeightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
        deliveryPackageDestCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        deliveryPackageContentCol.setCellValueFactory(new PropertyValueFactory<>("content"));
        packageSelectorCol.setCellValueFactory(param -> param.getValue().getSelectedProp());
        packageSelectionTable.setEditable(true);
        packageSelectorCol.setEditable(true);
        packageSelectorCol.setCellFactory(CheckBoxTableCell.forTableColumn(packageSelectorCol));

        // set up labels
        driverTabResponeLabel.setWrapText(true);
        vehicleTabResponeLabel.setWrapText(true);
        packageTabResponeLabel.setWrapText(true);
        deliveryTabResponeLabel.setWrapText(true);

        updateView();
    }

    public void handleDriverTabAction(ActionEvent actionEvent) {

        // preparing the view model to forward in dto
        viewModel.setSelectedDriver(driverTableView.getSelectionModel().getSelectedItem());
        viewModel.setDriverList(_logic.getAllDrivers());
        viewModel.getInputFieldValues().put(InputFieldKeys.DRIVER_NAME_INPUT_FIELD_KEY, driverNameInput.getText());

        // get database action
        var action = Arrays.stream(((Node)actionEvent.getSource()).getId().split("_")).collect(Collectors.toList()).get(0);

        // sending data to handler
        var dto = new MainViewDto(viewModel, action);
        _log.log(Level.INFO, "MainViewDto created: " + dto);
        var response = _mainActionHandler.getDriverActionHandler().handleAction(dto);

        // fetching changes from database to display
        driverTabResponeLabel.setTextFill(response.contains("success") ? Paint.valueOf("Green") : Paint.valueOf("Red"));
        driverTabResponeLabel.setText(response);
        updateView();
    }

    public void handleVehicleTabAction(ActionEvent actionEvent) {
        //preparing the view model to forward in dto
        viewModel.setSelectedVehicle(vehicleTable.getSelectionModel().getSelectedItem());
        viewModel.setVehicleList(_logic.getAllVehicles());
        viewModel.getInputFieldValues().put(InputFieldKeys.VEHICLE_PLATE_NUMBER_INPUT_FIELD_KEY, vehicleNumberInput.getText());
        viewModel.getInputFieldValues().put(InputFieldKeys.VEHICLE_MAX_CAPACITY_INPUT_FIELD_KEY, vehicleMaxCapInput.getValue().toString());

        // get database action
        var action =  Arrays.stream(((Node)actionEvent.getSource()).getId().split("_")).collect(Collectors.toList()).get(0);

        //sending data to handler
        var dto = new MainViewDto(viewModel, action);
        _log.log(Level.INFO, "MainViewDto created: " + dto);
        var response = _mainActionHandler.getVehicleActionHandler().handleAction(dto);

        // fetching changes
        vehicleTabResponeLabel.setTextFill(response.contains("success") ? Paint.valueOf("Green") : Paint.valueOf("Red"));
        vehicleTabResponeLabel.setText(response);
        updateView();
    }

    public void handlePackageTabAction(ActionEvent actionEvent) {
        //preparing the view model to forward in dto
        viewModel.setSelectedPackage(packageTable.getSelectionModel().getSelectedItem());
        viewModel.setPackageList(_logic.getAllPackages());
        viewModel.getInputFieldValues().put(InputFieldKeys.PACKAGE_WEIGHT_INPUT_FIELD_KEY, packageWeightInput.getValue().toString());
        viewModel.getInputFieldValues().put(InputFieldKeys.PACKAGE_CONTENT_INPUT_FIELD_KEY, packageContentInput.getText());
        viewModel.getInputFieldValues().put(InputFieldKeys.PACKAGE_DESTINATION_INPUT_FIELD_KEY, packageDestinationInput.getText());

        // get database action
        var action =  Arrays.stream(((Node)actionEvent.getSource()).getId().split("_")).collect(Collectors.toList()).get(0);

        //sending data to handler
        var dto = new MainViewDto(viewModel, action);
        _log.log(Level.INFO, "MainViewDto created: " + dto);
        var response = _mainActionHandler.getPackageActionHandler().handleAction(dto);

        // fetching changes
        packageTabResponeLabel.setTextFill(response.contains("success") ? Paint.valueOf("Green") : Paint.valueOf("Red"));
        packageTabResponeLabel.setText(response);
        updateView();
    }

    public void handleDeliveryTabAction(ActionEvent actionEvent) {
        _log.log(Level.INFO, "SelectedPackages: " + packageSelectionTable.getItems().stream().filter(p -> p.getSelectedProp().get()).collect(Collectors.toList()));

        //preparing the view model to forward in dto
        var deliverySelected = deliveryTable.getSelectionModel().getSelectedItem();
        viewModel.setSelectedDelivery(deliverySelected);
        viewModel.setSelectedDriver(driverSelector.getSelectionModel().getSelectedItem());
        viewModel.setSelectedVehicle(vehicleSelector.getSelectionModel().getSelectedItem());

        viewModel.setPackageList(packageSelectionTable.getItems());

        // get database action
        var action =  Arrays.stream(((Node)actionEvent.getSource()).getId().split("_")).collect(Collectors.toList()).get(0);

        //sending data to handler
        var dto = new MainViewDto(viewModel, action);
        _log.log(Level.INFO, "MainViewDto created: " + dto);
        var response = _mainActionHandler.getDeliveryActionHandler().handleAction(dto);

        // fetching changes
        deliveryTabResponeLabel.setTextFill(response.contains("success") ? Paint.valueOf("Green") : Paint.valueOf("Red"));
        deliveryTabResponeLabel.setText(response);
        if (action.equals(ActionSource.REMOVE)) {
            driverSelector.setValue(null);
            vehicleSelector.setValue(null);
        }
        updateView();
    }

    public void handleDeliverySelection(MouseEvent actionEvent) {
        var deliverySelected = deliveryTable.getSelectionModel().getSelectedItem();

        var packagesToDisplay = _logic.getAllPackages().stream().filter(p -> p.getDelivery() == null).collect(Collectors.toCollection(ArrayList::new));
        packagesToDisplay.addAll(
                _logic
                        .getAllPackages()
                        .stream()
                        .filter(p -> p.getDelivery() != null )
                        .filter(p -> p.getDelivery().getId() == deliverySelected.getId())
                .collect(Collectors.toList()));
        _log.log(Level.INFO, "packages available: " + packagesToDisplay);

        for (var item : packagesToDisplay) {
            item.setSelectedProp(item.isSelected());
        }

        packageSelectionTable.setItems(FXCollections.observableList(packagesToDisplay));
        driverSelector.setValue(deliverySelected != null ? deliverySelected.getDriver() : null);
        vehicleSelector.setValue(deliverySelected != null ? deliverySelected.getVehicle() : null);
    }

    private void updateView() {
        driverSelector.setItems(FXCollections.observableList(_logic.getAllDrivers()));
        vehicleSelector.setItems(FXCollections.observableList(_logic.getAllVehicles()));
        packageSelectionTable.setItems(FXCollections.observableList(_logic.getAllPackages().stream().filter(p -> p.getDelivery() == null).collect(Collectors.toList())));

        driverTableView.setItems(FXCollections.observableArrayList(_logic.getAllDrivers()));
        vehicleTable.setItems(FXCollections.observableList(_logic.getAllVehicles()));
        packageTable.setItems(FXCollections.observableList(_logic.getAllPackages()));

        deliveryTable.setItems(FXCollections.observableList(_logic.getAllDeliveries()));
    }

// region     ==== FXML fields ====

    // DriverTab

    @FXML
    private TableView<Driver> driverTableView;
    @FXML
    private TableColumn<Driver,Integer> driverIdCol;
    @FXML
    private TableColumn<Driver, String> driverNameCol;
    @FXML
    private TextField driverNameInput;

    // DeliveryTab

    @FXML
    private ChoiceBox<Driver> driverSelector;
    @FXML
    private TableView<Delivery> deliveryTable;
    @FXML
    private TableColumn<Delivery, Integer> deliveryIdCol;
    @FXML
    private TableColumn<Delivery, String> deliveryDriverNameCol;
    @FXML
    private TableColumn<Delivery, String> deliveryVehicleNumberCol;
    @FXML
    private TableView<Package> packageSelectionTable;
    @FXML
    private TableColumn<Package, Boolean> packageSelectorCol;
    @FXML
    private TableColumn<Package,Integer> deliveryPackageIdCol;
    @FXML
    private TableColumn<Package, String> deliveryPackageContentCol;
    @FXML
    private TableColumn<Package, Double> deliveryPackageWeightCol;
    @FXML
    private TableColumn<Package, String> deliveryPackageDestCol;
    @FXML
    private ChoiceBox<Vehicle> vehicleSelector;

    // VehicleTab

    @FXML
    private TextField vehicleNumberInput;
    @FXML
    private TableView<Vehicle> vehicleTable;
    @FXML
    private TableColumn<Vehicle, String> vehiclePlateCol;
    @FXML
    private TableColumn<Vehicle, Double> vehicleCapCol;
    @FXML
    private Spinner<Double> vehicleMaxCapInput;

    // PackageTab

    @FXML
    private TextField packageContentInput;
    @FXML
    private TextField packageDestinationInput;
    @FXML
    private TableView<Package> packageTable;
    @FXML
    private TableColumn<Package,String> packageContentCol;
    @FXML
    private TableColumn<Package,String> packageDestCol;
    @FXML
    private TableColumn<Package,Double> packageWeightCol;
    @FXML
    private Spinner<Double> packageWeightInput;

    // Labels

    @FXML
    private Label driverTabResponeLabel;
    @FXML
    private Label vehicleTabResponeLabel;
    @FXML
    private Label packageTabResponeLabel;
    @FXML
    private Label deliveryTabResponeLabel;

// endregion
}
