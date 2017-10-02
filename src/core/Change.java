package core;

/**
 * Created by Other on 7/14/2017.
 */
public class Change {

    public static PasswordEntry toEntry(Password p){
        PasswordEntry pwe = new PasswordEntry(
                p.getService(),
                p.getUsername(),
                p.getPassword(),
                p.getNotes(),
                p.getGenerationDate(),
                p.getEditDate(),
                false
                );
        return pwe;
    }

    public static Password toPassword(PasswordEntry pwe){
        Password pw = new Password(
                pwe.getService(),
                pwe.getUsername(),
                pwe.getPassword(),
                pwe.getNotes(),
                pwe.getGenerationDate(),
                pwe.getEditDate()
                );
        return pw;

    }
}
