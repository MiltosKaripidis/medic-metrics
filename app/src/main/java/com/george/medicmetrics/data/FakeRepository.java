package com.george.medicmetrics.data;

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
    public void getPatientList(Callback<List<Patient>> callback) {
        Record record = new Record();
        record.setId(1);
        record.setRespiratoryRate(15);
        record.setBloodOxygen(92);
        record.setBodyTemperature(36);
        record.setSystolicBloodPressure(105);
        record.setHearRate(71);
        record.setScore(3);
        record.setTimestamp("19/12/17 14:26");

        Record record2 = new Record();
        record2.setId(1);
        record2.setRespiratoryRate(19);
        record2.setBloodOxygen(100);
        record2.setBodyTemperature(38);
        record2.setSystolicBloodPressure(105);
        record2.setHearRate(67);
        record2.setScore(1);
        record2.setTimestamp("17/11/17 09:57");

        List<Record> recordList = new ArrayList<>();
        recordList.add(record);
        recordList.add(record2);

        Patient patient = new Patient();
        patient.setId(1);
        patient.setRecordList(recordList);

        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);

        callback.onSuccess(patientList);
    }
}
