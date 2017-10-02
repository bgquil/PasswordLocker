package controller;

import core.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import core.Main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Created by Other on 7/6/2017.
 */
public class LockerController {

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    private BooleanProperty openLocker;


     /*
    Begin FXML element declaration.
     */

    //Table
    @FXML
    private TableView<Password> passwordTable;
    @FXML
    private TableColumn<Password, String> svcCol;
    @FXML
    private TableColumn<Password, String> userCol;
    @FXML
    private TableColumn<Password, String> pwCol;
    @FXML
    private TableColumn<Password, LocalDateTime> genCol;
    @FXML
    private TableColumn<Password, LocalDateTime> editCol;

    // Edit Items
    @FXML
    private AnchorPane editPane;
    @FXML
    private GridPane editGrid;
    //Fields for edit area.
    @FXML
    private TextField svcField;
    @FXML
    private TextField usrField;
    @FXML
    private TextArea pwArea;
    @FXML
    private TextField genField;
    @FXML
    private TextField changeField;
    @FXML
    private TextArea noteArea;

    //buttons
    @FXML
    private Button newPWButton;
    @FXML
    private Button closeLockerButton;
    @FXML
    private Label lockerPathLabel;

    /*
    End FXML element declaration.
     */


    @FXML
    private void initialize() {

        //Setup Table
        initTable();
        //Setup Bindings
        initBindings();
        //Setup date formatting for generate/edit columns
        initDateFormatting();
        editPane.setDisable(true);

        initLoadLocker();


    }

    private void initTable(){
        svcCol.setCellValueFactory(cellData -> cellData.getValue().serviceProperty());
        userCol.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        pwCol.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
        genCol.setCellValueFactory(cellData -> cellData.getValue().generationDateProperty());
        editCol.setCellValueFactory(cellData -> cellData.getValue().editDateProperty());

        //Listen for selections in passwordTable and show the selected item in the edit pane
        passwordTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                handleDisplayPasswordInfoFromTable(
                        passwordTable.getSelectionModel().getSelectedIndex()
                );
            }
        });
    }

    private void initBindings(){
        openLocker = new SimpleBooleanProperty(false);
        newPWButton.visibleProperty().bind(openLocker);
        closeLockerButton.visibleProperty().bind(openLocker);
        editPane.visibleProperty().bind(openLocker);
    }

    private void initDateFormatting(){
        DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss" );

        // Custom rendering of genCol containing the generation date LocalDateTime Object
        genCol.setCellFactory(column -> {
            return new TableCell<Password, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Format date.
                        setText(myDateFormatter.format(item));

                        // Style all dates in March with a different color.
                    }
                }
            };
        });
        // Custom rendering of editCol containing the edit date LocalDateTime Object
        editCol.setCellFactory(column -> {
            return new TableCell<Password, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Format date.
                        setText(myDateFormatter.format(item));

                        // Style all dates in March with a different color.
                    }
                }
            };
        });
    }

    private void initLoadLocker(){
        loadPasswords();
        openLocker.setValue(true);
        lockerPathLabel.setText("\t"+Context.getInstance().getFilePath());

        editPane.setDisable(false);
    }

    private void handleDisplayPasswordInfoFromTable(int pwIndex){
        Password pw = passwordTable.getItems().get(pwIndex);
        svcField.setText(pw.getService());
        usrField.setText(pw.getUsername());
        pwArea.setText(pw.getPassword());
        genField.setText(pw.getEditDate().toString());
        changeField.setText(pw.getEditDate().toString());
        noteArea.setText(pw.getNotes());
    }

//    /**
//     * Called from handleOpenFile(). Attempts to open a locker file using a user-supplied key.
//     * If the key is incorrect or the file is not a locker file, the lockerReadError() will prompt.
//     * @param filePath the path of the file supplied by the user.
//     */
//    private void readFile(String filePath){
//        TextInputDialog dialog = new TextInputDialog("");
//        dialog.setTitle("Open Locker");
//        dialog.setContentText("Enter the key for the locker");
//
//        Optional<String> result = dialog.showAndWait();
//        if (result.isPresent()){
//            String key = result.get();
//
//            try {
//                Context.getInstance().setLocker(FileManagement.openLockerFile(filePath, key));
//                openLocker.setValue(true);
//                Context.getInstance().setFilePath(filePath);
//                Context.getInstance().setLockerKey(key);
//                loadPasswords();
//                lockerPathLabel.setText("\t"+filePath);
//
//                editPane.setDisable(false);
//
//            } catch (Exception e) {
//                lockerReadError();
//                readFile(filePath);
//            }
//        }
//        else{
//            dialog.close();
//        }
//    }

    private void loadPasswords(){
        passwordTable.getItems().clear();
        if (Context.getInstance().getLocker() != null)
            passwordTable.setItems(
                    FXCollections.observableArrayList(Context.getInstance().getLocker().getPasswords()));
    }


    private void saveFile(){

        FileManagement.writeFile(
                Context.getInstance().getLocker(),
                Context.getInstance().getFilePath(),
                Context.getInstance().getLockerKey()
        );
    }

    @FXML
    private void closeLocker(){
        lockerPathLabel.setText("");
        passwordTable.getItems().clear();
        editPane.setDisable(true);
        editPane.setDisable(true);

        svcField.clear();
        usrField.clear();
        pwArea.clear();
        genField.clear();
        changeField.clear();
        noteArea.clear();
        openLocker.setValue(false);
        saveFile();
        mainApp.showRecentLocker();
    }


    // TODO: 8/24/2017 setup save
    @FXML
    private void handleSave(){

        Password selected = passwordTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Update");
        alert.setContentText("Service: ID:"+selected.getService());
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            int index = passwordTable.getSelectionModel().getSelectedIndex();
            Password p = Context.getInstance().getLocker().modifyPassword(index);
            p.modifyPassword(
                    svcField.getText(),
                    usrField.getText(),
                    pwArea.getText(),
                    noteArea.getText());
            saveFile();
            loadPasswords();
        }
    }


    @FXML
    private void handleRemove(){
        Password selected = passwordTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Removal");
        alert.setContentText("Service: ID:"+selected.getService());
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            int index = passwordTable.getSelectionModel().getSelectedIndex();
            Context.getInstance().getLocker().removePassword(index);
            loadPasswords();
        }
        else{

        }


    }

    @FXML
    private void lockerReadError(){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Password Locker - Error");
        alert.setHeaderText("Error Occurred");
        alert.setContentText("There was an error opening the specified locker.\n" +
                "Either the specified file is not a locker or the provided key is incorrect.");
        alert.showAndWait();
    }

    /*
    Menu Items
     */

    @FXML
    private void handleNewLockerDialog() {
        System.out.println("1");
        mainApp.showNewLockerDialog();
        System.out.println("2");
    }

    /*
    * 	Open the Add Password Dialog.
    */
    @FXML
    private void handleShowNewPasswordDialog() {

        mainApp.showNewPasswordDialog();
        loadPasswords();
    }
}
