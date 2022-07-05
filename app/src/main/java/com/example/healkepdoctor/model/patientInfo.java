package com.example.healkepdoctor.model;

import android.os.Parcel;
import android.os.Parcelable;

public class patientInfo implements Parcelable {
    private String username;
    private String clientName;
    private int birth;
    private double height1;
    private double weight1;
    private String date;
    private String num;
    private int age;
    private String energy;
    private String aveenergy;
    private String enweight;
    private String lenweight;
    private String Diseases;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBirth() {
        return birth;
    }

    public void setBirth(int birth) {
        this.birth = birth;
    }

    public double getHeight1() {
        return height1;
    }

    public void setHeight1(double height1) {
        this.height1 = height1;
    }

    public double getWeight1() {
        return weight1;
    }

    public void setWeight1(double weight1) {
        this.weight1 = weight1;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }

    public String getAveenergy() {
        return aveenergy;
    }

    public void setAveenergy(String aveenergy) {
        this.aveenergy = aveenergy;
    }

    public String getEnweight() {
        return enweight;
    }

    public void setEnweight(String enweight) {
        this.enweight = enweight;
    }

    public String getLenweight() {
        return lenweight;
    }

    public void setLenweight(String lenweight) {
        this.lenweight = lenweight;
    }

    public String getDiseases() {
        return Diseases;
    }

    public void setDiseases(String diseases) {
        Diseases = diseases;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.clientName);
        dest.writeInt(this.birth);
        dest.writeDouble(this.height1);
        dest.writeDouble(this.weight1);
        dest.writeString(this.date);
        dest.writeString(this.num);
        dest.writeInt(this.age);
        dest.writeString(this.energy);
        dest.writeString(this.aveenergy);
        dest.writeString(this.enweight);
        dest.writeString(this.lenweight);
        dest.writeString(this.Diseases);
    }

    public void readFromParcel(Parcel source) {
        this.username = source.readString();
        this.clientName = source.readString();
        this.birth = source.readInt();
        this.height1 = source.readDouble();
        this.weight1 = source.readDouble();
        this.date = source.readString();
        this.num = source.readString();
        this.age = source.readInt();
        this.energy = source.readString();
        this.aveenergy = source.readString();
        this.enweight = source.readString();
        this.lenweight = source.readString();
        this.Diseases = source.readString();
    }

    public patientInfo() {
    }

    protected patientInfo(Parcel in) {
        this.username = in.readString();
        this.clientName = in.readString();
        this.birth = in.readInt();
        this.height1 = in.readDouble();
        this.weight1 = in.readDouble();
        this.date = in.readString();
        this.num = in.readString();
        this.age = in.readInt();
        this.energy = in.readString();
        this.aveenergy = in.readString();
        this.enweight = in.readString();
        this.lenweight = in.readString();
        this.Diseases = in.readString();
    }

    public static final Creator<patientInfo> CREATOR = new Creator<patientInfo>() {
        @Override
        public patientInfo createFromParcel(Parcel source) {
            return new patientInfo(source);
        }

        @Override
        public patientInfo[] newArray(int size) {
            return new patientInfo[size];
        }
    };
}
