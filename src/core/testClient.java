package core;

import javafx.fxml.FXML;
import javafx.scene.control.TextInputDialog;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.*;
import java.util.Arrays;

/**
 * Created by Other on 6/30/2017.
 */
public class testClient {

    public static void main(String[] args){
        Locker l = new Locker(true);

        Locker l2 = null;

            l.addPassword(new Password("amazon", "u","pw123","business",true));
            l.addPassword(new Password("Twitter", "u","pw123","business", true));
            l.addPassword(new Password("Twitter", "u","pw123","business", true));
            l.addPassword(new Password("TEST", "u","pw123","business", true));
            l.addPassword(new Password("BOA", "u","pw123","business", true));

            System.out.println(l.getPasswords().get(0).getGenerationDate());
            FileManagement.writeFile(l, "testLocker2.bin", "abc");
            System.out.println("=====Reading====");

        try {
            l2 = FileManagement.openLockerFile("testLocker2.bin", "abc");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(l2.getPasswords().get(0));

        System.out.println(l2.getPasswords().get(1));
        System.out.println(l2.getPasswords().get(4));


        System.out.println(System.getProperty("user.home"));
    }
}
