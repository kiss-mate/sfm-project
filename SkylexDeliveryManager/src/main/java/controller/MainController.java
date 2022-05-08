package controller;

import com.google.inject.Inject;
import common.constants.InputFieldKeys;
import common.exceptions.ArgumentNullException;
import data.Driver;
import handlers.IMainActionHandler;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import logic.ILogic;
import models.MainViewDto;
import models.MainViewModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {
    @FXML
    public Button button;

    public class TestModel {
        public BooleanProperty selected;
        public String name;

        public TestModel(boolean selected, String name) {
            this.selected = new SimpleBooleanProperty(selected);
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public BooleanProperty selectedProperty() {return selected; }

        @Override
        public String toString() {
            return "TestModel{" +
                    "selected=" + selected +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
    private final Logger _log;
    private final ILogic _logic;
    private final IMainActionHandler _mainActionHandler;
    private final MainViewModel viewModel;
    @FXML
    public TableView<TestModel> testTable;
    @FXML
    public TableColumn<TestModel, Boolean> selectCol;
    @FXML
    public TableColumn<TestModel, String> nameCol;


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
        driverIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        driverNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        selectCol.setCellValueFactory(param -> param.getValue().selectedProperty());
        selectCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectCol));

        selectCol.setEditable(true);
        testTable.setEditable(true);

        updateView();
    }

    public void handleButtonAction(ActionEvent actionevent) {
        _log.log(Level.INFO,"" + testTable.getItems().stream().filter(x -> x.selectedProperty().get()).toList());
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
        driverTableView.setItems(FXCollections.observableArrayList(_logic.getAllDrivers()));
        testTable.setItems(FXCollections.observableList(new ArrayList<>(List.of(
                new TestModel(false, "asd"),
                new TestModel(true, "fdghdj"),
                new TestModel(false, "rtztui")
        ))));
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
