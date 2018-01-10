package core;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Benjamin Quilliams on 6/29/2017.
 */
public class Credential implements Serializable {

    private String service;
    private String username;
    private String password;
    private String notes;
    private LocalDateTime generationDate = LocalDateTime.MIN;
    private LocalDateTime editDate = LocalDateTime.MIN;


    /**
     * Used in the creation of a new credential.
     * @param service the service to which the password belongs
     * @param username the username as part of the login credentials
     * @param password the password as part of the login credentials
     * @param notes miscellaneous user-provided notes
     */
    public Credential(String service, String username, String password, String notes){
        this.service = service;
        this.username = username;
        this.password = password;
        this.notes = notes;
        this.generationDate = this.editDate =  LocalDateTime.now();

    }



    /**
     * Used to instantiate existing password entries.
     * @param service the service to which the password belongs
     * @param username the username as part of the login credentials
     * @param password the password as part of the login credentials
     * @param notes miscellaneous user-provided notes
     */
    public Credential(String service, String username, String password, String notes,
                      LocalDateTime genDate, LocalDateTime editDate){
        this.service = service;
        this.username = username;
        this.password = password;
        this.notes = notes;
        this.generationDate = genDate;
        this.editDate = editDate;
    }


    /**
     * Changes the password for the credential.
     * @param newPassword the new password.
     */
    public void updatePassword(String newPassword){
        this.password = newPassword;
        this.editDate = LocalDateTime.now();
    }

    public void modifyCredential(String service, String username, String password, String notes){
        this.service = service;
        this.username = username;
        if (!this.password.equals(password))
            this.password = password;
            this.editDate = LocalDateTime.now();
        this.notes = notes;
    }


    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getGenerationDate(){
        return this.generationDate;
    }

    public LocalDateTime getEditDate(){
        return this.editDate;
    }

    public String toString(){
        return "Service: "+ this.service+
                "\nUsername: "+ this.username +
                "\nPassword: "+ this.password+
                "\nNotes: "+ this.notes;
    }


    @Override
    public boolean equals(Object o){
        if (o == this)
                return true;
        if (!(o instanceof Credential))
            return false;
        Credential c = (Credential) o;
        return c.service.equals(service) &&
                c.username.equals(username) &&
                c.password.equals(password) &&
                c.notes.equals(c);

    }

    /*
    Begin JavaFX properties
     */

    public StringProperty serviceProperty(){
        return new SimpleStringProperty(this.service);
    }

    public StringProperty usernameProperty(){
        return new SimpleStringProperty(this.username);
    }

    public StringProperty passwordProperty(){
        return new SimpleStringProperty(this.password);
    }

    public ObjectProperty<LocalDateTime> generationDateProperty(){ return new SimpleObjectProperty(this.generationDate);
    }

    public ObjectProperty<LocalDateTime> editDateProperty(){
        return new SimpleObjectProperty(this.editDate);
    }

    public StringProperty notesProperty(){
        return new SimpleStringProperty(this.notes);
    }
}
