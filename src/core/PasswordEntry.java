package core;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

/**
 * Created by Other on 7/14/2017.
 */
public class PasswordEntry {

    private StringProperty service;
    private StringProperty username;
    private StringProperty password;
    private StringProperty notes;
    private ObjectProperty<LocalDateTime> generationDate;
    private ObjectProperty<LocalDateTime> editDate;



    /**
     * Used in the creation of a new password entry.
     *
     * @param service
     * @param username
     * @param password
     * @param notes
     */
    public PasswordEntry(String service, String username, String password, String notes,
                         LocalDateTime genDate, LocalDateTime editDate, boolean newPW) {
        this.service = new SimpleStringProperty(service);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.notes = new SimpleStringProperty(notes);
        this.generationDate = new SimpleObjectProperty<LocalDateTime>(genDate);
        this.editDate = new SimpleObjectProperty<LocalDateTime>(editDate);
        if (newPW){

        }
        else{

        }
    }




    public String getService() {
        return service.get();
    }

    public void setService(String service) {
        this.service.set(service);
    }

    public StringProperty serviceProperty(){
        return service;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public StringProperty usernameProperty(){
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty passwordProperty(){
        return password;
    }

    public String getNotes() {
        return notes.get();
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    public StringProperty notesProperty(){
        return notes;
    }

    public LocalDateTime getGenerationDate(){
        return generationDate.get();
    }

    public void setGenerationDate(LocalDateTime genDateTime){
        this.generationDate.set(genDateTime);
    }

    public ObjectProperty<LocalDateTime> generationDateProperty(){
        return generationDate;
    }

    public LocalDateTime getEditDate(){
        return editDate.get();
    }

    public void setEditDate(LocalDateTime editDateTime){
        this.editDate.set(editDateTime);
    }

    public ObjectProperty<LocalDateTime> editDateProperty(){
        return editDate;
    }


    public void updatePassword(String newPassword) {
        setPassword(newPassword);
        setEditDate(LocalDateTime.now());
    }


    public String toString() {
        return "Service: " + this.service +
                "\nUsername: " + this.username +
                "\nPassword: " + this.password +
                "\nNotes: " + this.notes;
    }


    // TODO: 7/14/2017 Equals Method cause of table listener not updating
//    public boolean equals(Object o) {
//        if (this == o)
//            return true;
//        if (o == null)
//            return false;
//        return true;
//    }
}
