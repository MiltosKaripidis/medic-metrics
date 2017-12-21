package com.george.medicmetrics.data;

public interface DatabaseSchema {

    String DATABASE_NAME = "medic_metrics.db";
    int DATABASE_VERSION = 1;

    interface PatientTable {

        String NAME = "patient";
        String CREATE = "create table if not exists " + NAME + " ( "
                + Column.ID + " integer primary key, "
                + Column.NAME + " text, "
                + Column.LAST_NAME + " text, "
                + Column.USERNAME + " text, "
                + Column.PASSWORD + " text"
                + ")";

        interface Column {

            String ID = "id";
            String NAME = "name";
            String LAST_NAME = "last_name";
            String USERNAME = "username";
            String PASSWORD = "password";
        }
    }
}
