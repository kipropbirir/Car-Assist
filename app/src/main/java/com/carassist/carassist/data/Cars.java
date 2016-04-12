package com.carassist.carassist.data;

/**
 * Created by user on 3/16/2016.
 */
public class Cars {
    private String carModel;
    private String bodyPaint;
    private String mileage;
    private String description;
    private String bodyType;
    private String uniqueId;
    private String pushId;

    public Cars(){}

    public Cars(String carModel,String bodyPaint,String bodyType,String mileage,String description,String uniqueId,String pushId){

        this.carModel = carModel;
        this.bodyPaint = bodyPaint;
        this.bodyType = bodyType;
        this.mileage = mileage;
        this.description = description;
        this.uniqueId = uniqueId;
        this.pushId = pushId;

    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getBodyPaint() {
        return bodyPaint;
    }

    public void setBodyPaint(String bodyPaint) {
        this.bodyPaint = bodyPaint;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
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
