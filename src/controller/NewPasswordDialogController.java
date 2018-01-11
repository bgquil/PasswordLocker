package controller;

import core.Context;
import core.Credential;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Benjamin Quilliams on 7/17/2017.
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
        svcField.textProperty().addListener((observable, oldValue, newValue) -> {

        });

        noteArea.textProperty().addListener((observable, oldValue, newValue) -> {

        });

        svcField.textProperty().addListener((observable, oldValue, newValue) -> {
        });



    }

    @FXML
    private void handleAddPassword(){
        if (svcField.getText().equals("") || usrField.getText().equals("") || pwArea.getText().equals("") ){
            emptyFieldError();
        }
        else {
            Credential newCredential = new Credential(
                    svcField.getText(),
                    usrField.getText(),
                    pwArea.getText(),
                    noteArea.getText()
                    );
            Context.getInstance().getLocker().addCredential(newCredential);
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
        alert.setTitle("Credential Locker - Error");
        alert.setHeaderText("Empty Fields");
        alert.setContentText("The service, username, and password fields may not be empty");

        alert.showAndWait();
    }

}
