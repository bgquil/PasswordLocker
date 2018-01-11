package core;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by Benjamin Quilliams on 6/29/2017.
 */

    public class Locker implements Serializable {

    private ArrayList<Credential> credentials;
    private LocalDateTime generationDate;
    private String name;

    /**
     * Used to generate a new locker.
     * @param name User provided name for the locker
     */
    public Locker(String name){
        this.name = name;
        generationDate = LocalDateTime.now();
        credentials = new ArrayList<Credential>();
    }

    /**
     * Returns the list of credentials in the locker.
     * @return an ArrayList containing credentials.
     */
    public ArrayList<Credential> getCredentials(){
        return this.credentials;
    }

    /**
     * Adds a new credential to the locker.
     * @param c the credential to be added
     */
    public void addCredential(Credential c){
        credentials.add(c);
    }

    /**
     * Removes the credential at the provided index.
     * @param index the index of the credential to be removed
     */
    public void removeCredential(int index){
        credentials.remove(index);
    }

    /**
     * Obtains a password object at a given index.
     * @param index the provided index from which to fetch the locker.
     * @return a Credential object
     */
    public Credential getCredential(int index) {return credentials.get(index);}

    /**
     * Modifies a password at a given index in the locker.
     * @param index the index of the password in the locker.
     * @param service the service to which the password belongs
     * @param username the username as part of the login credentials
     * @param password the password as part of the login credentials
     * @param note miscellaneous user-provided notes
     */
    public void modifyCredential(int index, String service, String username, String password, String note) {
        Credential p = getCredential(index);
        p.modifyCredential(service, username, password, note);
    }



}
