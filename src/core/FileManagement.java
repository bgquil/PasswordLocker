package core;

import com.sun.org.apache.xpath.internal.SourceTree;

import javax.crypto.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Other on 6/30/2017.
 */
public class FileManagement {
    ;
    private static final String userHome = System.getProperty("user.home");
    public static final String configDirectory = userHome+"/.passwordlocker/";
    public static final String configFile = configDirectory+"locker.ini";

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

    private static SealedObject readFile(String path, String key) throws IOException, ClassNotFoundException {
        Cipher cipher = Crypto.getCipherDecrypt(key);
        CipherInputStream cis = new CipherInputStream(new FileInputStream(path), cipher);
        ObjectInputStream ois = new ObjectInputStream(cis);
        SealedObject sealedObject;
        sealedObject = (SealedObject) ois.readObject();
        return sealedObject;
    }

    public static void writeFile(Locker locker, String path, String key) {
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calls firstRun() to determine if the configuration file exists, then attempts to
     * read locker.ini for Lockers recently used.
     * @return List containing Strings with the directory of any Lockers.
     */
    public static List<String> readStartup(){
        List<String> lockerList = new ArrayList<>();
        try {
            firstRun();
            Scanner input = new Scanner(new File(configFile));
            while (input.hasNext()){
                lockerList.add(input.nextLine());
            }
            return lockerList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lockerList;
}

    /**
     * Determines if the configuration directory and configuration file have been created.
     * If not, they are created.
     * @throws IOException
     */
    private static void firstRun() throws IOException {

        File dir = new File(configDirectory);
        File config = new File(configFile);
        if (!dir.exists())
            new File(configDirectory).mkdir();
        if (!config.exists())
            config.createNewFile();
    }


}
