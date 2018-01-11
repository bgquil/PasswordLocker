package testing;

import core.Credential;
import core.FileManagement;
import core.Locker;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin Quilliams on 6/30/2017.
 */
public class testClient {

    public static void main(String[] args){
        List<String> l = new ArrayList<>();
        l.add("C:/Users/Other/Desktop/testLocker/testLocker2.bin");
        l.add("C:/Users/Other/Desktop/testLocker/testLocker3.bin");
        l.add("C:/Users/Other/Desktop/testLocker/testLocker123.bin");

        try {
            FileManagement.writeLockerList(l);

            List<String> readList = FileManagement.readLockerList();
            System.out.println(readList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private static void lockerTest(){
        Locker l = new Locker("Test Locker");

        Locker l2 = null;

        l.addCredential(new Credential("amazon", "u","pw123","business"));
        l.addCredential(new Credential("Twitter", "u","pw123","business"));
        l.addCredential(new Credential("Twitter", "u","pw123","business"));
        l.addCredential(new Credential("TEST", "u","pw123","business"));
        l.addCredential(new Credential("BOA", "u","pw123","business"));

        System.out.println(l.getCredentials().get(0).getGenerationDate());
        try {
            FileManagement.writeFile(l, "testLocker2.bin", "abc");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
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
        System.out.println(l2.getCredentials().get(0));

        System.out.println(l2.getCredentials().get(1));
        System.out.println(l2.getCredentials().get(4));


        System.out.println(FileManagement.configDirectory);
        System.out.println(FileManagement.configFile);
    }
}
