package com.george.medicmetrics.data;

import com.george.medicmetrics.objects.Patient;

import java.util.List;

public interface DataSource {

    void getPatientList(Callback<List<Patient>> callback);
}
