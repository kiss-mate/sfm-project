package controller;

import com.google.inject.Inject;
import common.constants.InputFieldKeys;
import common.exceptions.ArgumentNullException;
import data.Driver;
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
        updateView();
    }

    public void handleDriverTabAction(ActionEvent actionEvent) {

        // preparing the view model to forward in dto
        viewModel.setSelectedDriver(driverTableView.getSelectionModel().getSelectedItem());
        viewModel.setDriverList(_logic.getAllDrivers());
        viewModel.getInputFieldValues().put(InputFieldKeys.DRIVER_NAME_INPUT_FIELD_KEY, driverNameInput.getText());

        // get database action
        var action = ((Node)actionEvent.getSource()).getId();

        // sending data to handler
        var dto = new MainViewDto(viewModel, action);
        _log.log(Level.INFO, "MainViewDto created: " + dto);
        var response = _mainActionHandler.getDriverActionHandler().handleAction(dto);

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
