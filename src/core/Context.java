package core;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Other on 6/30/2017.
 */
public class Context {
    private final static Context instance = new Context();
    private Locker locker;
    private String lockerKey;
    private String filePath;
    private List<String> recentLockers;

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

    public List<String> getRecentLockers(){
        return this.recentLockers;
    }


    public void setRecentLockers(List<String> list){ this.recentLockers = list;}

    public void clearContext(){
        this.locker = null;
        this.lockerKey = null;
        this.filePath = null;
        this.recentLockers = null;
    }
}
