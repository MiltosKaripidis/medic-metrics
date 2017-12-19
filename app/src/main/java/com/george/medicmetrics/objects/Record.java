package com.george.medicmetrics.objects;

public class Record {

    private int mId;
    private double mRespiratoryRate;
    private double mBloodOxygen;
    private double mBodyTemperature;
    private double SystolicBloodPressure;
    private double mHearRate;
    private int mScore;
    private String mTimestamp;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public double getRespiratoryRate() {
        return mRespiratoryRate;
    }

    public void setRespiratoryRate(double respiratoryRate) {
        mRespiratoryRate = respiratoryRate;
    }

    public double getBloodOxygen() {
        return mBloodOxygen;
    }

    public void setBloodOxygen(double bloodOxygen) {
        mBloodOxygen = bloodOxygen;
    }

    public double getBodyTemperature() {
        return mBodyTemperature;
    }

    public void setBodyTemperature(double bodyTemperature) {
        mBodyTemperature = bodyTemperature;
    }

    public double getSystolicBloodPressure() {
        return SystolicBloodPressure;
    }

    public void setSystolicBloodPressure(double systolicBloodPressure) {
        SystolicBloodPressure = systolicBloodPressure;
    }

    public double getHearRate() {
        return mHearRate;
    }

    public void setHearRate(double hearRate) {
        mHearRate = hearRate;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public String getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(String timestamp) {
        mTimestamp = timestamp;
    }
}
