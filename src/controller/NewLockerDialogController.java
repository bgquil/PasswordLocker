package controller;

import core.Context;
import core.Password;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by Other on 7/17/2017.
 */
public class NewLockerDialogController {

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private TextField locField;
    @FXML
    private TextField pwField;
    @FXML
    private TextField pwConfirmField;
    @FXML
    private Label pwConfirmLabel;


    @FXML
    private void initialize(){

    }

    @FXML
    private void handleOpenFile(){
        FileChooser f = new FileChooser();
        f.setTitle("Open Locker");

        File file = f.showSaveDialog(new Stage());
        if (file != null){
            //fileName.setText(file.getAbsolutePath());
//            readFile(file.getAbsolutePath());
            locField.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void handleCreateLocker(){

    }
    @FXML
    private void handleCancelDialog(){
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
