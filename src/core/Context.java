package core;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


/**
 * Created by Other on 6/30/2017.
 */
public class Context {
    private final static Context instance = new Context();
    private Locker locker;
    private String lockerKey;
    private String filePath;
    private String[] recentLockers = new String[5];

    private BooleanProperty openLocker = new SimpleBooleanProperty(false);


    public static Context getInstance(){
        return instance;
    }

    public Locker getLocker(){
        return this.locker;
    }

    public void setLocker(Locker l){
        this.locker = l;
    }

    public String getLockerKey(){
        return this.lockerKey;
    }

    public void setLockerKey(String key){
        this.lockerKey = key;
    }

    public String getFilePath(){ return this.filePath;}

    public void setFilePath(String path){ this.filePath = path;}

    public String[] getRecentLockers(){
        return this.recentLockers;
    }

    public void setRecentLockers(String[] a){
        this.recentLockers = a;
    }

    public void clearContext(){
        this.locker = null;
        this.lockerKey = null;
        this.filePath = null;
        this.recentLockers = null;
    }
}
