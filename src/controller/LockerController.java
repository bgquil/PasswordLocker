package controller;

import core.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import core.Main;

import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Created by Benjamin Quilliams on 7/6/2017.
 */
public class LockerController {

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    private BooleanProperty credentialSelected;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss" );
    private LocalDateTime now = LocalDateTime.now();
    private LocalDateTime sixMonths = now.plusDays(-10);
    private LocalDateTime year = now.plusDays(-365);

     /*
    Begin FXML element declaration.
     */

    //Table
    @FXML
    private TableView<Credential> passwordTable;
    @FXML
    private TableColumn<Credential, String> svcCol;
    @FXML
    private TableColumn<Credential, String> userCol;
    @FXML
    private TableColumn<Credential, String> pwCol;
    @FXML
    private TableColumn<Credential, LocalDateTime> genCol;
    @FXML
    private TableColumn<Credential, LocalDateTime> editCol;

    // Edit Items
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
    @FXML
    private Button removePasswordButton;
    @FXML
    private Button copyPasswordButton;
    @FXML
    private Button savePasswordButton;


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
               credentialSelected.setValue(true);
            }
        });
    }

    private void initBindings(){
        credentialSelected = new SimpleBooleanProperty(false);
        copyPasswordButton.visibleProperty().bind(credentialSelected);
        removePasswordButton.visibleProperty().bind(credentialSelected);
        savePasswordButton.visibleProperty().bind(credentialSelected);
    }

    private void initDateFormatting(){


        // Custom rendering of genCol containing the generation date LocalDateTime Object
        genCol.setCellFactory(column -> {
            return new TableCell<Credential, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Format date.
                        setText(dateFormatter.format(item));


                    }
                }
            };
        });
        // Custom rendering of editCol containing the edit date LocalDateTime Object
        editCol.setCellFactory(column -> {
            return new TableCell<Credential, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Format date.
                        setText(dateFormatter.format(item));
                    }
                }
            };
        });
    }

    private void initLoadLocker(){
        loadPasswords();
        lockerPathLabel.setText("\t"+Context.getInstance().getFilePath());
    }

    private void handleDisplayPasswordInfoFromTable(int pwIndex){
        changeField.setStyle("-fx-background-color: white;");
        Credential pw = passwordTable.getItems().get(pwIndex);
        svcField.setText(pw.getService());
        usrField.setText(pw.getUsername());
        pwArea.setText(pw.getPassword());
        genField.setText(dateFormatter.format(pw.getEditDate()));
        changeField.setText(dateFormatter.format(pw.getEditDate()));
        noteArea.setText(pw.getNotes());
        // Visual notification of old passwords.
        if (pw.getEditDate().isBefore(year)){
            changeField.setStyle("-fx-text-box-border: red; -fx-text-fill: red;");
        }
        else if (pw.getEditDate().isBefore(sixMonths)){
            changeField.setStyle("-fx-text-box-border: yellow; -fx-text-fill: yellow;");
        }
    }


    private void loadPasswords(){
        passwordTable.getItems().clear();
        if (Context.getInstance().getLocker() != null)
            passwordTable.setItems(
                    FXCollections.observableArrayList(Context.getInstance().getLocker().getCredentials()));
    }


    private void saveFile(){
        Locker locker   =  Context.getInstance().getLocker();
        String path     =  Context.getInstance().getFilePath();
        String key      =  Context.getInstance().getLockerKey();

        try {
            FileManagement.writeFile(locker, path , key);

        } catch (IOException e) {
           lockerSaveError(path);
        } catch (IllegalBlockSizeException e) {
            lockerSaveError(path);
        }
    }

    @FXML
    private void closeLocker(){
        lockerPathLabel.setText("");
        passwordTable.getItems().clear();
        clearCredentialDetailsArea();
        saveFile();
        Context.getInstance().clearContext();
        mainApp.showRecentLocker();
    }

    @FXML
    private void clearCredentialDetailsArea() {
        svcField.clear();
        usrField.clear();
        pwArea.clear();
        genField.clear();
        changeField.clear();
        noteArea.clear();
    }

    @FXML
    private void handleSave(){

        Credential selected = passwordTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Update of " + selected.getService());
        alert.setContentText("Confirm Changes");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            int index = passwordTable.getSelectionModel().getSelectedIndex();
            Locker openLocker = Context.getInstance().getLocker();
            openLocker.modifyCredential(
                    index,
                    svcField.getText(),
                    usrField.getText(),
                    pwArea.getText(),
                    noteArea.getText());
            saveFile();
            loadPasswords();
            clearCredentialDetailsArea();
            passwordTable.getSelectionModel().select(index);
        }
    }


    @FXML
    private void handleRemove(){
        Credential selected = passwordTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Removal");
        alert.setContentText("Service: ID:"+selected.getService());
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            int index = passwordTable.getSelectionModel().getSelectedIndex();
            Context.getInstance().getLocker().removeCredential(index);
            loadPasswords();
        }
    }

    @FXML
    private void handleCopyPasswordClipboard() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(pwArea.getText());
        clipboard.setContent(content);

    }

    @FXML
    private void lockerReadError(){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Credential Locker - Error");
        alert.setHeaderText("Error Occurred");
        alert.setContentText("There was an error opening the specified locker.\n" +
                "Either the specified file is not a locker or the provided key is incorrect.");
        alert.showAndWait();
    }

    @FXML
    private void lockerSaveError(String path){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Credential Locker");
        alert.setHeaderText("There was an error.");
        alert.setContentText("The Locker could not be written to disk at: " + path);
    }

    /*
    Menu Items
     */


    /*
    * 	Open the Add Credential Dialog.
    */
    @FXML
    private void handleShowNewPasswordDialog() {

        mainApp.showNewPasswordDialog();
        loadPasswords();
    }
}
