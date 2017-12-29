package com.george.medicmetrics.data;

import android.support.annotation.NonNull;

import com.george.medicmetrics.objects.Patient;
import com.george.medicmetrics.objects.Record;

import java.util.ArrayList;
import java.util.List;

public class FakeRepository implements DataSource {

    private static FakeRepository sInstance;

    private FakeRepository() {
        // No instance
    }

    public static FakeRepository getInstance() {
        if (sInstance == null) {
            sInstance = new FakeRepository();
        }

        return sInstance;
    }

    @Override
    public void setPatientId(int patientId) {
        // TODO: Implement
    }

    @Override
    public int getPatientId() {
        return 0;
    }

    @Override
    public boolean isUserLoggedIn() {
        return false;
    }

    @Override
    public void validateUser(@NonNull String username, @NonNull String password, @NonNull Callback<Integer> callback) {
        // TODO: Implement
    }

    @Override
    public void setPatient(@NonNull Patient patient) {
        // Do nothing
    }

    @Override
    public void getPatientList(@NonNull Callback<List<Patient>> callback) {
        Record record = new Record();
        record.setId(1);
        record.setRespiratoryRate(15);
        record.setBloodOxygen(92);
        record.setBodyTemperature(36.6);
        record.setSystolicBloodPressure(105);
        record.setHearRate(71);
        record.setScore(3);
        record.setTimestamp("19/12/17 14:26");

        Record record2 = new Record();
        record2.setId(2);
        record2.setRespiratoryRate(19);
        record2.setBloodOxygen(100);
        record2.setBodyTemperature(38.2);
        record2.setSystolicBloodPressure(105);
        record2.setHearRate(67);
        record2.setScore(8);
        record2.setTimestamp("17/11/17 09:57");

        Record record3 = new Record();
        record3.setId(3);
        record3.setRespiratoryRate(11);
        record3.setBloodOxygen(82);
        record3.setBodyTemperature(37.1);
        record3.setSystolicBloodPressure(110);
        record3.setHearRate(83);
        record3.setScore(5);
        record3.setTimestamp("20/12/17 15:59");

        List<Record> recordList = new ArrayList<>();
        recordList.add(record);
        recordList.add(record2);
        recordList.add(record3);

        Patient patient = new Patient();
        patient.setId(1);
        patient.setUsername("asdf");
        patient.setPassword("fasd");
        patient.setName("Takis");
        patient.setLastName("Papadopoulos");
        patient.setRecordList(recordList);

        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);

        callback.onSuccess(patientList);
    }

    @Override
    public void setRecord(int patientId, @NonNull Record record) {
        // TODO: Implement
    }

    @Override
    public void getRecordList(int patientId, @NonNull Callback<List<Record>> callback) {
        // TODO: Implement
    }
}
