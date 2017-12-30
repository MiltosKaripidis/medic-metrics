package com.george.medicmetrics.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.george.medicmetrics.objects.Patient;
import com.george.medicmetrics.objects.Record;

import java.util.ArrayList;
import java.util.List;

public class LocalRepository extends SQLiteOpenHelper {

    public LocalRepository(Context context) {
        super(context, DatabaseSchema.DATABASE_NAME, null, DatabaseSchema.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseSchema.PatientTable.CREATE);
        db.execSQL(DatabaseSchema.RecordTable.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Do nothing
    }

    void savePatient(@NonNull Patient patient) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSchema.PatientTable.Column.NAME, patient.getName());
        contentValues.put(DatabaseSchema.PatientTable.Column.LAST_NAME, patient.getLastName());
        contentValues.put(DatabaseSchema.PatientTable.Column.USERNAME, patient.getUsername());
        contentValues.put(DatabaseSchema.PatientTable.Column.PASSWORD, patient.getPassword());

        getWritableDatabase().insert(DatabaseSchema.PatientTable.NAME, null, contentValues);
    }

    void deletePatient(int patientId) {
        deleteRecords(patientId);

        String query = "delete from " + DatabaseSchema.PatientTable.NAME
                + " where id = " + patientId;
        getWritableDatabase().execSQL(query);
    }

    private void deleteRecords(int patientId) {
        String query = "delete from " + DatabaseSchema.RecordTable.NAME
                + " where patient_id = " + patientId;
        getWritableDatabase().execSQL(query);
    }

    int getPatientId(@NonNull String username, @NonNull String password) {
        int patientId = -1;
        String query = "select * from " + DatabaseSchema.PatientTable.NAME
                + " where " + DatabaseSchema.PatientTable.Column.USERNAME + " = '" + username + "'"
                + " and " + DatabaseSchema.PatientTable.Column.PASSWORD + " = '" + password + "'";

        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        if (cursor.moveToFirst()) {
            patientId = cursor.getInt(cursor.getColumnIndex(DatabaseSchema.PatientTable.Column.ID));
        }
        cursor.close();

        return patientId;
    }

    void saveRecord(int patientId, @NonNull Record record) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSchema.RecordTable.Column.PATIENT_ID, patientId);
        contentValues.put(DatabaseSchema.RecordTable.Column.RESPIRATORY_RATE, record.getRespiratoryRate());
        contentValues.put(DatabaseSchema.RecordTable.Column.BLOOD_OXYGEN, record.getBloodOxygen());
        contentValues.put(DatabaseSchema.RecordTable.Column.BODY_TEMPERATURE, record.getBodyTemperature());
        contentValues.put(DatabaseSchema.RecordTable.Column.SYSTOLIC_BLOOD_PRESSURE, record.getSystolicBloodPressure());
        contentValues.put(DatabaseSchema.RecordTable.Column.HEART_RATE, record.getHearRate());
        contentValues.put(DatabaseSchema.RecordTable.Column.CATHETER_USED, record.isCatheterUsed() ? 1 : 0);
        if (record.isCatheterUsed()) {
            contentValues.put(DatabaseSchema.RecordTable.Column.MILLILITER_PER_HOUR, record.getMilliliterPerHour());
        }
        contentValues.put(DatabaseSchema.RecordTable.Column.CONSCIOUSNESS_LEVEL, record.getConsciousnessLevel());
        contentValues.put(DatabaseSchema.RecordTable.Column.OXYGEN_SUPPLEMENTED, record.isOxygenSupplemented() ? 1 : 0);
        contentValues.put(DatabaseSchema.RecordTable.Column.SCORE, record.getScore());
        contentValues.put(DatabaseSchema.RecordTable.Column.CLINICAL_CONCERN, record.getClinicalConcern());
        contentValues.put(DatabaseSchema.RecordTable.Column.TIMESTAMP, record.getTimestamp());

        getWritableDatabase().insert(DatabaseSchema.RecordTable.NAME, null, contentValues);
    }

    List<Record> getRecordList(int patientId) {
        List<Record> recordList = new ArrayList<>();

        String query = "select * from " + DatabaseSchema.RecordTable.NAME
                + " where " + DatabaseSchema.RecordTable.Column.PATIENT_ID + " = " + patientId;

        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        if (!cursor.moveToFirst()) {
            cursor.close();
            return recordList;
        }

        do {
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseSchema.RecordTable.Column.ID));
            double respiratoryRate = cursor.getDouble(cursor.getColumnIndex(DatabaseSchema.RecordTable.Column.RESPIRATORY_RATE));
            double bloodOxygen = cursor.getDouble(cursor.getColumnIndex(DatabaseSchema.RecordTable.Column.BLOOD_OXYGEN));
            double bodyTemperature = cursor.getDouble(cursor.getColumnIndex(DatabaseSchema.RecordTable.Column.BODY_TEMPERATURE));
            double systolicBloodPressure = cursor.getDouble(cursor.getColumnIndex(DatabaseSchema.RecordTable.Column.SYSTOLIC_BLOOD_PRESSURE));
            double heartRate = cursor.getDouble(cursor.getColumnIndex(DatabaseSchema.RecordTable.Column.HEART_RATE));
            int catheterUsed = cursor.getInt(cursor.getColumnIndex(DatabaseSchema.RecordTable.Column.CATHETER_USED));
            int milliliterPerHour = cursor.getInt(cursor.getColumnIndex(DatabaseSchema.RecordTable.Column.MILLILITER_PER_HOUR));
            String consciousnessLevel = cursor.getString(cursor.getColumnIndex(DatabaseSchema.RecordTable.Column.CONSCIOUSNESS_LEVEL));
            int oxygenSupplemented = cursor.getInt(cursor.getColumnIndex(DatabaseSchema.RecordTable.Column.OXYGEN_SUPPLEMENTED));
            int score = cursor.getInt(cursor.getColumnIndex(DatabaseSchema.RecordTable.Column.SCORE));
            int clinicalConcern = cursor.getInt(cursor.getColumnIndex(DatabaseSchema.RecordTable.Column.CLINICAL_CONCERN));
            String timestamp = cursor.getString(cursor.getColumnIndex(DatabaseSchema.RecordTable.Column.TIMESTAMP));

            Record record = new Record();
            record.setId(id);
            record.setRespiratoryRate(respiratoryRate);
            record.setBloodOxygen(bloodOxygen);
            record.setBodyTemperature(bodyTemperature);
            record.setSystolicBloodPressure(systolicBloodPressure);
            record.setHearRate(heartRate);
            record.setCatheterUsed(catheterUsed == 1);
            if (catheterUsed == 1) {
                record.setMilliliterPerHour(milliliterPerHour);
            }
            record.setConsciousnessLevel(consciousnessLevel);
            record.setOxygenSupplemented(oxygenSupplemented == 1);
            record.setScore(score);
            record.setClinicalConcern(clinicalConcern);
            record.setTimestamp(timestamp);

            recordList.add(record);
        } while (cursor.moveToNext());
        cursor.close();

        return recordList;
    }
}
