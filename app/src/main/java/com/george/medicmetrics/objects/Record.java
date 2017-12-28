package com.george.medicmetrics.objects;

import android.support.annotation.IntDef;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Record implements Serializable {

    @IntDef({CLINICAL_CONCERN_LOW, CLINICAL_CONCERN_MEDIUM, CLINICAL_CONCERN_HIGH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ClinicalConcern {
        // Empty
    }

    public static final int CLINICAL_CONCERN_LOW = 0;
    public static final int CLINICAL_CONCERN_MEDIUM = 1;
    public static final int CLINICAL_CONCERN_HIGH = 2;

    private int mId;
    private double mRespiratoryRate;
    private double mBloodOxygen;
    private double mBodyTemperature;
    private double SystolicBloodPressure;
    private double mHearRate;
    private boolean mCatheterUsed;
    private int milliliterPerHour;
    private String mConsciousnessLevel;
    private boolean mOxygenSupplemented;
    private int mScore;
    private String mTimestamp;
    private int mClinicalConcern;

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

    public boolean isCatheterUsed() {
        return mCatheterUsed;
    }

    public void setCatheterUsed(boolean catheterUsed) {
        mCatheterUsed = catheterUsed;
    }

    public int getMilliliterPerHour() {
        return milliliterPerHour;
    }

    public void setMilliliterPerHour(int milliliterPerHour) {
        this.milliliterPerHour = milliliterPerHour;
    }

    public String getConsciousnessLevel() {
        return mConsciousnessLevel;
    }

    public void setConsciousnessLevel(String consciousnessLevel) {
        mConsciousnessLevel = consciousnessLevel;
    }

    public boolean isOxygenSupplemented() {
        return mOxygenSupplemented;
    }

    public void setOxygenSupplemented(boolean oxygenSupplemented) {
        mOxygenSupplemented = oxygenSupplemented;
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

    @ClinicalConcern
    public int getClinicalConcern() {
        return mClinicalConcern;
    }

    public void setClinicalConcern(@ClinicalConcern int clinicalConcern) {
        mClinicalConcern = clinicalConcern;
    }
}
