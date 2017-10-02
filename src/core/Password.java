package core;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Other on 6/29/2017.
 */
public class Password implements Serializable {

    private String service;
    private String username;
    private String password;
    private String notes;
    private LocalDateTime generationDate = LocalDateTime.MIN;
    private LocalDateTime editDate = LocalDateTime.MIN;


    /**
     * Used in the creation of a new password entry.
     * @param service
     * @param username
     * @param password
     * @param notes
     * @param newPW
     */
    public Password(String service, String username, String password, String notes, boolean newPW){
        this.service = service;
        this.username = username;
        this.password = password;
        this.notes = notes;
        //firstRun
        if (newPW) {
            this.generationDate = this.editDate =  LocalDateTime.now();
        }
    }



    /**
     * Used to instantiate existing password entries.
     * @param service the service to which the password belongs
     * @param username the username as part of the login credentials
     * @param password the password as part of the login credentials
     * @param notes misc notes
     */
    public Password(String service, String username, String password, String notes,
                    LocalDateTime genDate, LocalDateTime editDate){
        this.service = service;
        this.username = username;
        this.password = password;
        this.notes = notes;
        this.generationDate = genDate;
        this.editDate = editDate;
    }


    public void updatePassword(String newPassword){
        this.password = newPassword;
        this.editDate = LocalDateTime.now();
    }

    public void modifyPassword(String service, String username, String password, String notes){
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

//    public boolean equals(Object o){
//        if (this == o)
//                return true;
//        if (o == null)
//            return false;
//        return true;
//    }

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
