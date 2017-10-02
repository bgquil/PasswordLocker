package controller;

import core.Context;
import core.FileManagement;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import core.Main;
import javafx.scene.control.TextInputDialog;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class RecentLockerController {

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }


    @FXML
    private ListView<String> recentLockerList;




    @FXML
    private void initialize(){
        initRecentLockerListView();



    }


    private void initRecentLockerListView() {
        List<String> lockerList = FileManagement.readStartup();
        recentLockerList.setItems(FXCollections.observableArrayList(lockerList));
    }

    private void listCheck(){
        for (int i = 0; i < recentLockerList.getItems().size(); i++){
            if (!fileExists(recentLockerList.getItems().get(i))){

            }

        }
    }

    private boolean fileExists(String path){
        File f = new File(path);
        if (f.exists())
            return true;
        return false;
    }


    @FXML
    private void handleOpenLocker() {
        String lockerPath = null;
        if (!recentLockerList.getSelectionModel().isEmpty()) {
            lockerPath = recentLockerList.getSelectionModel().getSelectedItem().toString();
        }

        if (!lockerPath.equals(null) && fileExists(lockerPath)) {
            Context.getInstance().setFilePath(lockerPath);
            if (readFile(lockerPath))
                mainApp.initLockerView();
        } else {
            fileReadError();
        }
    }


    /**
     * Called from handleOpenFile(). Attempts to open a locker file using a user-supplied key.
     * If the key is incorrect or the file is not a locker file, the lockerReadError() will prompt.
     * @param filePath the path of the file supplied by the user.
     */
    private boolean readFile(String filePath){
        Dialog<String> dialog = new TextInputDialog();

        dialog.setTitle("Open Locker");
        dialog.setContentText("Enter the key for the locker");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            String key = result.get();
            try {
                Context.getInstance().setLocker(FileManagement.openLockerFile(filePath, key));
                Context.getInstance().setFilePath(filePath);
                Context.getInstance().setLockerKey(key);
                return true;
            } catch (Exception e) {
                lockerReadError();
            }
        }
        else{
            dialog.close();
        }
        return false;
    }


    @FXML
    private void handleCreateLocker(){
        System.out.println("abc");

        mainApp.showNewLockerDialog();

    }

    @FXML
    private void handleImportLocker(){

    }



    @FXML
    private void fileReadError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Password Locker - Error");
        alert.setHeaderText("File not found");
        alert.setContentText("The locker at the given path was not found.");
        alert.showAndWait();
    }

    @FXML
    private void lockerReadError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Password Locker - Error");
        alert.setHeaderText("Error Occurred");
        alert.setContentText("There was an error opening the specified locker.\n" +
                "Either the specified file is not a locker or the provided key is incorrect.");
        alert.showAndWait();
    }

    /*
 * 	Close the Application
 */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
