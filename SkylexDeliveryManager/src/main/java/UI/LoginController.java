package UI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;

public class LoginController extends Application {

    @FXML
    private Button LoginButton;
    @FXML
    private Button CancelButton;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Label label;

    public void LoginAction(ActionEvent e){

    }
    public void CancelAction(ActionEvent e){
        System.out.println("you logged out");

        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();


    }




}


