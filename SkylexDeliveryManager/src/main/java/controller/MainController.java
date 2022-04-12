package controller;

import com.google.inject.Inject;
import common.exceptions.ArgumentNullException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import logic.ILogic;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {
    private final Logger _log;
    private final ILogic _logic;

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
    }

    public void handleSubmitAction(ActionEvent actionEvent) {
        _log.log(Level.INFO, "Submit button clicked");
        try {
            _logic.addDriver(driverNameInput.getText());
            _log.log(Level.INFO, "Submit action handled");
        } catch (RuntimeException ex) {
            _log.log(Level.SEVERE, "Error while handling submit action", ex);
        }
    }
}
