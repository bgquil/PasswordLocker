package controller;

import core.Context;
import core.FileManagement;
import core.Locker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.IllegalBlockSizeException;
import java.io.File;
import java.io.IOException;

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
    private TextField nameField;
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
        String extension = ".lok";
        File file = f.showSaveDialog(new Stage());
        if (file != null){

            locField.setText(file.getAbsolutePath()+extension);
        }
    }

    @FXML
    private void handleCreateLocker(){
        String path = locField.getText();
        String name = nameField.getText();
        String pw = pwField.getText();
        String pwConfirm = pwConfirmField.getText();

        if (!pw.equals(pwConfirm)){
            pwMatchError();
        }
        else if (pwField.getText().isEmpty() || pwConfirmField.getText().isEmpty() || locField.getText().isEmpty())
            emptyFieldError();
        else{
            Locker defaultLocker = new Locker(name);
            try {
                // Write locker and add it to recent locker list.
                FileManagement.writeFile(defaultLocker, path, pw);
                Context.getInstance().getRecentLockers().add(FileManagement.pathFix(path));
                lockerCreationInformation(path);
            } catch (IOException e) {
                lockerCreationError(path);
            } catch (IllegalBlockSizeException e) {
                lockerCreationInformation(path);
            }

            handleCancelDialog();

        }

    }


    @FXML
    private void handleCancelDialog(){
        this.dialogStage.close();

    }

   /*
   *
   * Begin Error Dialogs
   *
   *
    */

    @FXML
    private void emptyFieldError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Credential Locker - Error");
        alert.setHeaderText("Empty Fields");
        alert.setContentText("The location, password, and password confirmation field may not be empty.");
        alert.showAndWait();
    }

    @FXML
    private void pwMatchError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Credential Locker - Error");
        alert.setHeaderText("The provided passwords do not match.");
        alert.setContentText("The password field and password confirmation field must be the same.");
        alert.showAndWait();
    }

    @FXML
    private void lockerCreationInformation(String path){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Credential Locker");
        alert.setHeaderText("A new Locker has been created.");
        alert.setContentText("New Locker location: " + path);
        alert.showAndWait();
    }

    @FXML
    private void lockerCreationError(String path){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Credential Locker");
        alert.setHeaderText("There was an error.");
        alert.setContentText("A Locker could not be created at: " + path);
    }

}
