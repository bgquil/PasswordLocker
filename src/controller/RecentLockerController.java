package controller;

import core.Context;
import core.FileManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import core.Main;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
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
        Context.getInstance().setRecentLockers(lockerList);
    }

    private void applyRecentLockerListChanges(){
        try {
            FileManagement.writeLockerList(Context.getInstance().getRecentLockers());
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialize();
    }

    @FXML
    private void handleOpenLocker() {
        String lockerPath = null;
        if (!recentLockerList.getSelectionModel().isEmpty()) {
            lockerPath = recentLockerList.getSelectionModel().getSelectedItem().toString();
        }

        if (!lockerPath.equals(null) && new File(lockerPath).exists()) {
            Context.getInstance().setFilePath(lockerPath); // write new locker to recent lockers
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
        mainApp.showNewLockerDialog();
        //reinitialize RecentLockerController to update recent list.
        applyRecentLockerListChanges();
        initialize();
//        RECENT_LOCKER_LIST_PATH.getSelectionModel().selectLast();
//        handleOpenLocker();

    }

    @FXML
    private void handleImportLocker(){
        FileChooser f = new FileChooser();
        f.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                        "Locker File", "*.lok"));
        f.setTitle("Add Existing Locker");
        File file = f.showOpenDialog(new Stage());

        if (file.exists()){
            System.out.println("exists");
            System.out.println(file.getAbsolutePath());
            Context.getInstance().getRecentLockers().add(
                    FileManagement.pathFix(file.getAbsolutePath()));
            applyRecentLockerListChanges();
        }
    }

    @FXML
    private void handleRemoveLocker(){
        if (!recentLockerList.getSelectionModel().isEmpty()) {
            int lockerListIndex = recentLockerList.getSelectionModel().getSelectedIndex();
            String lockerPath = recentLockerList.getSelectionModel().getSelectedItem();

            Alert removeAlert = new Alert(Alert.AlertType.CONFIRMATION);
            removeAlert.setTitle("Removal Confirmation");
            removeAlert.setHeaderText("Remove a locker");
            removeAlert.setContentText("Would you like to remove the locker at: \n"
                    + lockerPath);

            Optional<ButtonType> result = removeAlert.showAndWait();

            if (result.get() == ButtonType.OK) {
                Context.getInstance().getRecentLockers().remove(lockerListIndex);
                applyRecentLockerListChanges();
            } else {
                removeAlert.close();
            }
        }


    }



    /*
    Begin Error Dialogs
     */

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
