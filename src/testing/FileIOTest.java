package testing;

import core.FileManagement;

/**
 * @author Benjamin Quilliams
 * 1/10/2018
 */
public class FileIOTest {

    public static void main(String[] args){
        String s = "C:\\Users\\Other\\Desktop\\newLockerpath\\manuallyAddedLocker.lok";
        System.out.println(s);
        System.out.println(FileManagement.pathFix(s));
    }
}
