package com.george.medicmetrics.objects;

import java.util.List;

public class Patient {

    private int mId;
    private String mName;
    private String mLastName;
    private String mUsername;
    private String mPassword;
    private List<Record> mRecordList;

    public Patient() {
        // Default constructor.
    }

    public Patient(String name, String lastName, String username, String password) {
        mName = name;
        mLastName = lastName;
        mUsername = username;
        mPassword = password;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public List<Record> getRecordList() {
        return mRecordList;
    }

    public void setRecordList(List<Record> recordList) {
        mRecordList = recordList;
    }
}
