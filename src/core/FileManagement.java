package core;

import javafx.stage.FileChooser;

import javax.crypto.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Other on 6/30/2017.
 */
public class FileManagement {

    private static final String userHome = System.getProperty("user.home");
    public static final String configDirectory = userHome+"/.passwordlocker/";
    public static final String configFile = configDirectory+"locker.config";
    public static final String RECENT_LOCKER_LIST_PATH = configDirectory+"lockers";
    public static final FileChooser.ExtensionFilter fileFilter = new FileChooser.ExtensionFilter(
            "Locker File", "*.lok");

    /**
     * Returns a Locker object if the provided file in the path is an encrypted, serialized Locker and the password
     * will successfully decrypt the file.
     * @param path the file to be decrypted.
     * @param key the user-provided key to decrypt the file.
     * @return A Locker object.
     */
    public static Locker openLockerFile(String path, String key) throws
            ClassNotFoundException, BadPaddingException, IllegalBlockSizeException, IOException {
        Cipher cipher = Crypto.getCipherDecrypt(key);
        SealedObject obj = readFile(path, key);

        return (Locker) obj.getObject(cipher);

    }

    /**
     * Attemps to create a SealedObject, assuming it contains a Locker object, from the given file with the given key.
     * @param path - the path of the provided .lok file
     * @param key - the provided user password for a given locker.
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static SealedObject readFile(String path, String key) throws IOException, ClassNotFoundException {
        Cipher cipher = Crypto.getCipherDecrypt(key);
        CipherInputStream cis = new CipherInputStream(new FileInputStream(path), cipher);
        ObjectInputStream ois = new ObjectInputStream(cis);
        SealedObject sealedObject;
        sealedObject = (SealedObject) ois.readObject();
        return sealedObject;
    }

    /**
     * Writes a SealedObject to disk.
     * @param locker the Locker object to write
     * @param path location to write to
     * @param key encryption/decryption key provided by use.
     * @throws IOException
     * @throws IllegalBlockSizeException
     */
    public static void writeFile(Locker locker, String path, String key) throws IOException, IllegalBlockSizeException {
            Cipher cipher = Crypto.getCipherEncrypt(key);
            SealedObject sealedObject = new SealedObject(locker, cipher );

            FileOutputStream fos = new FileOutputStream(path);
            CipherOutputStream cos =
                    new CipherOutputStream(fos, cipher);
            ObjectOutputStream os = new ObjectOutputStream(cos);

            os.writeObject(sealedObject);

            os.flush(); os.close();
            cos.flush(); cos.close();
            fos.flush(); fos.close();
    }

    /**
     * Calls firstRun() to determine if the configuration file exists, then attempts to
     * read locker.config for Lockers recently used.
     * @return List containing Strings with the directory of any Lockers.
     */
    public static List<String> readStartup(){
        try {
            firstRun(); // check if config and recent locker list exists
            return readLockerList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){

        }
        return new ArrayList<String>();
}

    /**
     * Determines if the configuration directory and configuration file have been created.
     * If not, they are created.
     * @throws IOException
     */
    private static void firstRun() throws IOException {
        File dir = new File(configDirectory);
        File config = new File(configFile);
        File recentLockers = new File(RECENT_LOCKER_LIST_PATH);
        if (!dir.exists())
            new File(configDirectory).mkdir();
        if (!config.exists()) {
            config.createNewFile();
            recentLockers.createNewFile();
        }
    }


    /**
     * Checks for any occurrences of "\" within a path and converts them to "/".
     * @param path the path to to be checked
     */
    public static String pathFix(String path){
        StringBuilder sb = new StringBuilder(path);
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '\\')
                sb.replace(i, i+1, "/");
        }
        return sb.toString();
    }

    /**
     * Write a serialized ArrayList containing locker paths to a file.
     * @param recentLockerList The list of lockers paths.
     * @throws IOException
     */
    public static void writeLockerList(List<String> recentLockerList) throws IOException {
        FileOutputStream fos = new FileOutputStream(RECENT_LOCKER_LIST_PATH);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(recentLockerList);
        os.flush(); os.close();
        fos.flush(); fos.close();

    }

    public static List<String> readLockerList() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(RECENT_LOCKER_LIST_PATH);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList readObject = (ArrayList) ois.readObject();
        return readObject;
    }
}
