package controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import common.exceptions.ArgumentNullException;
import data.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import logic.ILoginLogic;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class LoginController {
    @FXML
    public Label usernameLabel;
    @FXML
    public Label passwordLabel;
    @FXML
    public Button loginButton;
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button cancelButton;

    private final Logger _log;
    private final ILoginLogic _loginLogic;

    private static final String DEFAULT_USER = "./default_user.json";

    @Inject
    public LoginController(Logger log, ILoginLogic loginLogic) {
        if ((_log = log) == null) throw new ArgumentNullException("log");
        if ((_loginLogic = loginLogic) == null) throw new ArgumentNullException("loginLogic");
    }

    @FXML
    public void initialize() {
        var user = getUserFromFile(DEFAULT_USER);
        if (user != null && user.rememberUserName()) {
            usernameField.setText(user.getUsername());
        }
    }

    public void LoginAction(ActionEvent e){
        var registeredUser = getUserFromFile(DEFAULT_USER);
        if (registeredUser != null) {
            if (usernameField.getText().equals(registeredUser.getUsername())
                    && passwordField.getText().equals(registeredUser.getPassword())) {
                ((Stage)loginButton
                        .getScene()
                        .getWindow())
                        .close();
            }
        }
    }
  
    public void CancelAction(ActionEvent e){
        System.exit(0);
    }

    private User getUserFromFile(String file) {
        var om = new ObjectMapper();
        try {
            return om.readValue(new File(file), User.class);
        } catch (IOException e) {
            return null;
        }
    }
}
