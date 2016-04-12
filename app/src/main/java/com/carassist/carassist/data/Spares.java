package com.carassist.carassist.data;

/**
 * Created by user on 3/16/2016.
 */
public class Spares {

    private String name;
    private String pic;
    private String price;
    private String location;
    private String description;
    private String uniqueId;
    private String pushId;

    public Spares(){}

    public Spares(String name,String pic,String price,String location,String description,String uniqueId,String pushId){

        this.name = name;
        this.pic = pic;
        this.price = price;
        this.location = location;
        this.description = description;
        this.uniqueId = uniqueId;
        this.pushId = pushId;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
