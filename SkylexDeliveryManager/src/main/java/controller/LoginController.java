package controller;
import com.google.inject.Inject;
import common.exceptions.ArgumentNullException;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import logic.ILoginLogic;

import java.util.logging.Logger;

public class LoginController {
    public Label usernameLabel;
    public Label passwordLabel;
    public Button loginButton;
    public TextField usernameField;
    public PasswordField passwordField;
    public Button cancelButton;

    private final Logger _log;
    private final ILoginLogic _loginLogic;

    @Inject
    public LoginController(Logger log, ILoginLogic loginLogic) {
        if ((_log = log) == null) throw new ArgumentNullException("log");
        if ((_loginLogic = loginLogic) == null) throw new ArgumentNullException("loginLogic");
    }

    public void LoginAction(ActionEvent e){
        if (usernameField.getText().equals("admin") && passwordField.getText().equals("pwd")) {
            ((Stage)loginButton
                    .getScene()
                    .getWindow())
                    .close();
        }

        usernameField.setBorder(Border.EMPTY);

    }
  
    public void CancelAction(ActionEvent e){
        ((Stage)cancelButton
                .getScene()
                .getWindow())
        .close();
    }
}
