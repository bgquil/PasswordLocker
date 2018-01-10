package controller;


import core.Main;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;



/**
 * Created by Other on 7/6/2017.
 */
public class RootLayoutController {

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    /*
     *	Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Credential Locker - About");
        alert.setHeaderText("About");
        alert.setContentText("This is an about page");

        alert.showAndWait();
    }


    /*
    Menu Items
     */

    @FXML
    private void handleOpenLocker(){
        mainApp.showRecentLocker();
    }

    @FXML
    private void handleNewLockerDialog() {
        mainApp.showNewLockerDialog();
    }

    /*
     * 	Close the Application
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }

}
