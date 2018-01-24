package com.george.medicmetrics.ui.report;

import com.george.medicmetrics.R;
import com.george.medicmetrics.data.Callback;
import com.george.medicmetrics.data.DataSource;
import com.george.medicmetrics.objects.Record;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportPresenterTest {

    private static final int RECORD_ID = 1;
    private static final int PATIENT_ID = 1;
    private static final double RESPIRATORY_RATE = 1;
    private static final String EXPECTED_RESPIRATORY_RATE = "1";
    private static final double BLOOD_OXYGEN = 1;
    private static final String EXPECTED_BLOOD_OXYGEN = "1";
    private static final double BODY_TEMPERATURE = 1;
    private static final String EXPECTED_BODY_TEMPERATURE = "1.0";
    private static final double SYSTOLIC_BLOOD_PRESSURE = 1;
    private static final String EXPECTED_SYSTOLIC_BLOOD_PRESSURE = "1";
    private static final double HEART_RATE = 1;
    private static final String EXPECTED_HEART_RATE = "1";
    private static final String CONSCIOUSNESS_LEVEL = "consciousness_level";
    private static final int SCORE = 1;
    private static final String EXPECTED_SCORE = "1";
    private static final String TIMESTAMP = "timestamp";
    private static final int MILLILITER_PER_HOUR = 1;
    private static final String EXPECTED_MILLILITER_PER_HOUR = "1";

    private ReportPresenter mPresenter;

    @Mock
    private DataSource mDataSource;

    @Mock
    private ReportContract.View mView;

    @Captor
    private ArgumentCaptor<Callback<Record>> mCallbackArgumentCaptor;

    @Before
    public void setupPresenter() {
        mPresenter = new ReportPresenter(mDataSource);
        mPresenter.attachView(mView);
    }

    @Test
    public void loadRecord_fails() {
        when(mDataSource.getPatientId()).thenReturn(PATIENT_ID);

        mPresenter.loadRecord(RECORD_ID);

        verify(mDataSource).getRecord(anyInt(), anyInt(), mCallbackArgumentCaptor.capture());
        mCallbackArgumentCaptor.getValue().onFailure();
    }

    @Test
    public void loadRecord_notShowsMilliliterPerHour_whenNoCatheterUsed() {
        Record record = createRecord();
        when(mDataSource.getPatientId()).thenReturn(PATIENT_ID);

        mPresenter.loadRecord(RECORD_ID);

        verify(mDataSource).getRecord(anyInt(), anyInt(), mCallbackArgumentCaptor.capture());
        mCallbackArgumentCaptor.getValue().onSuccess(record);
        showDetails();
    }

    @Test
    public void loadRecord_showsMilliliterPerHour_whenCatheterUsed() {
        Record record = createRecord();
        record.setCatheterUsed(true);
        record.setMilliliterPerHour(MILLILITER_PER_HOUR);
        when(mDataSource.getPatientId()).thenReturn(PATIENT_ID);

        mPresenter.loadRecord(RECORD_ID);

        verify(mDataSource).getRecord(anyInt(), anyInt(), mCallbackArgumentCaptor.capture());
        mCallbackArgumentCaptor.getValue().onSuccess(record);
        showDetails();
        verify(mView).showUrineOutput(EXPECTED_MILLILITER_PER_HOUR);
    }

    private void showDetails() {
        verify(mView).showRespiratoryRate(EXPECTED_RESPIRATORY_RATE);
        verify(mView).showBloodOxygen(EXPECTED_BLOOD_OXYGEN);
        verify(mView).showBodyTemperature(EXPECTED_BODY_TEMPERATURE);
        verify(mView).showSystolicBloodPressure(EXPECTED_SYSTOLIC_BLOOD_PRESSURE);
        verify(mView).showHeartRate(EXPECTED_HEART_RATE);
        verify(mView).showConsciousnessLevel(CONSCIOUSNESS_LEVEL);
        verify(mView).showScore(EXPECTED_SCORE);
        verify(mView).showTimestamp(TIMESTAMP);
        verify(mView).changeCardColor(R.color.red);
        verify(mView).showDescription(R.string.clinical_concern_high);
    }

    private Record createRecord() {
        Record record = new Record();
        record.setRespiratoryRate(RESPIRATORY_RATE);
        record.setBloodOxygen(BLOOD_OXYGEN);
        record.setBodyTemperature(BODY_TEMPERATURE);
        record.setSystolicBloodPressure(SYSTOLIC_BLOOD_PRESSURE);
        record.setHearRate(HEART_RATE);
        record.setConsciousnessLevel(CONSCIOUSNESS_LEVEL);
        record.setScore(SCORE);
        record.setTimestamp(TIMESTAMP);
        record.setColor(R.color.red);
        record.setDescription(R.string.clinical_concern_high);
        return record;
    }
}
