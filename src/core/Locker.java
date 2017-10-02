package core;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by Other on 6/29/2017.
 */

//serializable
    public class Locker implements Serializable{

    private ArrayList<Password> passwords;
//    private int count = 0;
    private LocalDateTime generationDate;

    public Locker(boolean isNew){
        if (isNew)
            generationDate = LocalDateTime.now();
        passwords = new ArrayList<Password>();
    }

    public ArrayList<Password> getPasswords(){
        return this.passwords;
    }

    public void addPassword(Password p){
        passwords.add(p);
    }
    public void removePassword(int index){
        passwords.remove(index);
    }

    public Password modifyPassword(int index) {return passwords.get(index);}



}
