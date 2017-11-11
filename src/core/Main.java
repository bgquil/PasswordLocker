package core;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private AnchorPane startAnchor;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Password Locker");

        showRecentLocker();

    }

    private void initRootLayout() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/RootLayoutView.fxml"));

            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.centerOnScreen();
            primaryStage.show();

        } catch(IOException e) {

            e.printStackTrace();
        }
    }

    private void showLockerView(){
        try {
            primaryStage.setResizable(true);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/LockerView.fxml"));

            AnchorPane lockerView = (AnchorPane) loader.load();

            rootLayout.setCenter(lockerView);

            LockerController controller = loader.getController();
            controller.setMainApp(this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void initLockerView(){
        initRootLayout();
        showLockerView();

    }

    public void showRecentLocker() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/RecentLockerView.fxml"));

            startAnchor = loader.load();

            Scene scene = new Scene(startAnchor);
            primaryStage.setScene(scene);

            RecentLockerController controller = loader.getController(); // Loader must get controller
            controller.setMainApp(this);
            primaryStage.centerOnScreen();
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void showNewPasswordDialog(){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/NewPasswordDialogView.fxml"));
            AnchorPane newPasswordDialog = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Password");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(newPasswordDialog);
            dialogStage.setScene(scene);

            NewPasswordDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showNewLockerDialog(){

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/NewLockerDialogView.fxml"));
            AnchorPane newLockerDialog = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Locker");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(newLockerDialog);
            dialogStage.setScene(scene);

            NewLockerDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
