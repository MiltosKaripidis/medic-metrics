package com.george.medicmetrics.data;

public interface DatabaseSchema {

    String DATABASE_NAME = "medic_metrics.db";
    int DATABASE_VERSION = 1;

    interface PatientTable {

        String NAME = "Patient";
        String CREATE = "create table if not exists " + NAME + " ("
                + Column.ID + " integer primary key, "
                + Column.NAME + " text, "
                + Column.LAST_NAME + " text, "
                + Column.USERNAME + " text, "
                + Column.PASSWORD + " text);";

        interface Column {

            String ID = "id";
            String NAME = "name";
            String LAST_NAME = "last_name";
            String USERNAME = "username";
            String PASSWORD = "password";
        }
    }

    interface RecordTable {

        String NAME = "Record";
        String CREATE = "create table if not exists " + NAME + " ("
                + Column.ID + " integer primary key, "
                + Column.PATIENT_ID + " integer, "
                + Column.RESPIRATORY_RATE + " real, "
                + Column.BLOOD_OXYGEN + " real, "
                + Column.BODY_TEMPERATURE + " real, "
                + Column.SYSTOLIC_BLOOD_PRESSURE + " real, "
                + Column.HEART_RATE + " real, "
                + Column.CATHETER_USED + " integer, "
                + Column.MILLILITER_PER_HOUR + " integer, "
                + Column.CONSCIOUSNESS_LEVEL + " text, "
                + Column.OXYGEN_SUPPLEMENTED + " integer, "
                + Column.SCORE + " integer, "
                + Column.CLINICAL_CONCERN + " integer, "
                + Column.COLOR + " integer, "
                + Column.DESCRIPTION + " integer, "
                + Column.TIMESTAMP + " text, "
                + " foreign key (patient_id) references Patient(id));";

        interface Column {

            String ID = "id";
            String PATIENT_ID = "patient_id";
            String RESPIRATORY_RATE = "respiratory_rate";
            String BLOOD_OXYGEN = "blood_oxygen";
            String BODY_TEMPERATURE = "body_temperature";
            String SYSTOLIC_BLOOD_PRESSURE = "systolic_blood_pressure";
            String HEART_RATE = "heart_rate";
            String CATHETER_USED = "catheter_used";
            String MILLILITER_PER_HOUR = "milliliter_per_hour";
            String CONSCIOUSNESS_LEVEL = "consciousness_level";
            String OXYGEN_SUPPLEMENTED = "oxygen_supplemented";
            String SCORE = "score";
            String CLINICAL_CONCERN = "clinical_concern";
            String COLOR = "color";
            String DESCRIPTION = "description";
            String TIMESTAMP = "timestamp";
        }
    }
}
