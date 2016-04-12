package com.carassist.carassist.data;

/**
 * Created by user on 3/16/2016.
 */
public class Garage {

    private String name;
    private String description;
    private String location;
    private String uniqueId;
    private String pushId;

    public Garage(){}

    public Garage(String name,String description,String location,String uniqueId,String pushId){
        this.name = name;
        this.description = description;
        this.location = location;
        this.uniqueId = uniqueId;
        this.pushId = pushId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

}
