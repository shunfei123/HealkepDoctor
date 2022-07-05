package com.example.healkepdoctor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;
import java.util.Date;

public class dietPlan implements Parcelable {
    private String targetUsername;
    private String doctorUsername;
    private String start;
    private String end;
    private int totalEnergy;
    private int totalFood;
    private String mainFood;
    private String vegetables;
    private String fruit;
    private String meatAndEgg;
    private String bean;
    private String milk;
    private String nut;
    private String oil;
    private String salt;
    public dietPlan(){
        this.mainFood = "0";
        this.vegetables = "0";
        this.fruit = "0";
        this.meatAndEgg = "0";
        this.bean = "0";
        this.milk = "0";
        this.nut = "0";
        this.oil = "0";
        this.salt = "0";
    }
    public dietPlan(String targetUsername, String doctorUsername, String start, String end, int totalEnergy, int totalFood, String mainFood, String vegetables, String fruit, String meatAndEgg, String bean, String milk, String nut, String oil, String salt) {
        this.targetUsername = targetUsername;
        this.doctorUsername = doctorUsername;
        this.start = start;
        this.end = end;
        this.totalEnergy = totalEnergy;
        this.totalFood = totalFood;
        this.mainFood = mainFood;
        this.vegetables = vegetables;
        this.fruit = fruit;
        this.meatAndEgg = meatAndEgg;
        this.bean = bean;
        this.milk = milk;
        this.nut = nut;
        this.oil = oil;
        this.salt = salt;
    }

    public String getTargetUsername() {
        return targetUsername;
    }

    public void setTargetUsername(String targetUsername) {
        this.targetUsername = targetUsername;
    }

    public String getDoctorUsername() {
        return doctorUsername;
    }

    public void setDoctorUsername(String doctorUsername) {
        this.doctorUsername = doctorUsername;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getTotalEnergy() {
        return totalEnergy;
    }

    public void setTotalEnergy(int totalEnergy) {
        this.totalEnergy = totalEnergy;
    }

    public int getTotalFood() {
        return totalFood;
    }

    public void setTotalFood(int totalFood) {
        this.totalFood = totalFood;
    }

    public String getMainFood() {
        return mainFood;
    }

    public void setMainFood(String mainFood) {
        this.mainFood = mainFood;
    }

    public String getVegetables() {
        return vegetables;
    }

    public void setVegetables(String vegetables) {
        this.vegetables = vegetables;
    }

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public String getMeatAndEgg() {
        return meatAndEgg;
    }

    public void setMeatAndEgg(String meatAndEgg) {
        this.meatAndEgg = meatAndEgg;
    }

    public String getBean() {
        return bean;
    }

    public void setBean(String bean) {
        this.bean = bean;
    }

    public String getMilk() {
        return milk;
    }

    public void setMilk(String milk) {
        this.milk = milk;
    }

    public String getNut() {
        return nut;
    }

    public void setNut(String nut) {
        this.nut = nut;
    }

    public String getOil() {
        return oil;
    }

    public void setOil(String oil) {
        this.oil = oil;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.targetUsername);
        dest.writeString(this.doctorUsername);
        dest.writeString(this.start);
        dest.writeString(this.end);
        dest.writeInt(this.totalEnergy);
        dest.writeInt(this.totalFood);
        dest.writeString(this.mainFood);
        dest.writeString(this.vegetables);
        dest.writeString(this.fruit);
        dest.writeString(this.meatAndEgg);
        dest.writeString(this.bean);
        dest.writeString(this.milk);
        dest.writeString(this.nut);
        dest.writeString(this.oil);
        dest.writeString(this.salt);
    }

    public void readFromParcel(Parcel source) {
        this.targetUsername = source.readString();
        this.doctorUsername = source.readString();
        this.start = source.readString();
        this.end = source.readString();
        this.totalEnergy = source.readInt();
        this.totalFood = source.readInt();
        this.mainFood = source.readString();
        this.vegetables = source.readString();
        this.fruit = source.readString();
        this.meatAndEgg = source.readString();
        this.bean = source.readString();
        this.milk = source.readString();
        this.nut = source.readString();
        this.oil = source.readString();
        this.salt = source.readString();
    }

    protected dietPlan(Parcel in) {
        this.targetUsername = in.readString();
        this.doctorUsername = in.readString();
        this.start = in.readString();
        this.end = in.readString();
        this.totalEnergy = in.readInt();
        this.totalFood = in.readInt();
        this.mainFood = in.readString();
        this.vegetables = in.readString();
        this.fruit = in.readString();
        this.meatAndEgg = in.readString();
        this.bean = in.readString();
        this.milk = in.readString();
        this.nut = in.readString();
        this.oil = in.readString();
        this.salt = in.readString();
    }

    public static final Creator<dietPlan> CREATOR = new Creator<dietPlan>() {
        @Override
        public dietPlan createFromParcel(Parcel source) {
            return new dietPlan(source);
        }

        @Override
        public dietPlan[] newArray(int size) {
            return new dietPlan[size];
        }
    };
}
