package com.george.medicmetrics.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.george.medicmetrics.objects.Patient;

public class LocalRepository extends SQLiteOpenHelper {

    public LocalRepository(Context context) {
        super(context, DatabaseSchema.DATABASE_NAME, null, DatabaseSchema.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseSchema.PatientTable.CREATE);
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
}
