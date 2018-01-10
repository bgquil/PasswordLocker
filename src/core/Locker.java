package core;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by Benjamin Quilliams on 6/29/2017.
 */


    public class Locker implements Serializable{

    private ArrayList<Password> passwords;
    private LocalDateTime generationDate;
    private String name;

    /**
     * Used to generate a new locker.
     * @param name User provided name for the locker
     */
    public Locker(String name){
        this.name = name;
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

    /**
     * Obtains a password object at a given index.
     * @param index the provided index from which to fetch the locker.
     * @return a Password object.
     */
    public Password getPassword(int index) {return passwords.get(index);}


    public void modifyPassword(int index, String service, String username, String password, String note) {
        Password p = getPassword(index);
        p.modifyPassword(service, username, password, note);
    }



}
