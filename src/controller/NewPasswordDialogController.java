package controller;

import core.Context;
import core.Password;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Other on 7/17/2017.
 */
public class NewPasswordDialogController {

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private TextField svcField;
    @FXML
    private TextField usrField;
    @FXML
    private TextArea pwArea;
    @FXML
    private TextArea noteArea;


    @FXML
    private void initialize(){

    }

    @FXML
    private void handleAddPassword(){
        if (svcField.getText().equals("") || !usrField.getText().equals("") || !pwArea.getText().equals("") ){
            emptyFieldError();
        }
        else {
            Password newPW = new Password(
                    svcField.getText(),
                    usrField.getText(),
                    pwArea.getText(),
                    noteArea.getText(),
                    true);
            Context.getInstance().getLocker().addPassword(newPW);
            this.dialogStage.close();
        }
    }
    @FXML
    private void handleExitDialog(){
        this.dialogStage.close();

    }

    @FXML
    private void emptyFieldError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Password Locker - Error");
        alert.setHeaderText("Empty Fields");
        alert.setContentText("The service, username, and password fields may not be empty");

        alert.showAndWait();
    }

}
