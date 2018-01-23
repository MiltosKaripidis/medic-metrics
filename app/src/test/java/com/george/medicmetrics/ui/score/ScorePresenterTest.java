package com.george.medicmetrics.ui.score;

import com.george.medicmetrics.R;
import com.george.medicmetrics.data.DataSource;
import com.george.medicmetrics.objects.Record;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScorePresenterTest {

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
    private static final String YES = "Yes";
    private static final String NO = "No";
    private static final String CONSCIOUSNESS_LEVEL = "consciousness_level";
    private static final int SCORE = 1;
    private static final String EXPECTED_SCORE = "1";
    private static final String TIMESTAMP = "timestamp";
    private static final int MILLILITER_PER_HOUR = 1;
    private static final String EXPECTED_MILLILITER_PER_HOUR = "1";
    private static final int PATIENT_ID = 1;
    private ScorePresenter mPresenter;

    @Mock
    private DataSource mDataSource;

    @Mock
    private ScoreContract.View mView;

    @Before
    public void setupPresenter() {
        mPresenter = new ScorePresenter(mDataSource);
        mPresenter.attachView(mView);
    }

    @Test
    public void loadScore_noCatheterUsedAndClinicalConcernLow() {
        Record record = createRecord();
        record.setOxygenSupplemented(true);
        record.setClinicalConcern(Record.CLINICAL_CONCERN_LOW);

        mPresenter.loadScore(record);

        showDetails();
        verify(mView).showOxygenSupplemented(YES);
        verify(mView, never()).showUrineOutput(anyString());
        verify(mView).changeCardColor(R.color.green);
        verify(mView).showDescription(R.string.clinical_concern_low);
    }

    @Test
    public void loadScore_catheterUsedAndClinicalConcernMedium() {
        Record record = createRecord();
        record.setCatheterUsed(true);
        record.setMilliliterPerHour(MILLILITER_PER_HOUR);
        record.setClinicalConcern(Record.CLINICAL_CONCERN_MEDIUM);

        mPresenter.loadScore(record);

        showDetails();
        verify(mView).showOxygenSupplemented(NO);
        verify(mView).changeCardColor(R.color.orange);
        verify(mView).showDescription(R.string.clinical_concern_medium);
        verify(mView).showUrineOutput(EXPECTED_MILLILITER_PER_HOUR);
    }

    @Test
    public void loadScore_catheterUsedAndClinicalConcernHigh() {
        Record record = createRecord();
        record.setCatheterUsed(true);
        record.setMilliliterPerHour(MILLILITER_PER_HOUR);
        record.setClinicalConcern(Record.CLINICAL_CONCERN_HIGH);

        mPresenter.loadScore(record);

        showDetails();
        verify(mView).showOxygenSupplemented(NO);
        verify(mView).changeCardColor(R.color.red);
        verify(mView).showDescription(R.string.clinical_concern_high);
        verify(mView).showUrineOutput(EXPECTED_MILLILITER_PER_HOUR);
    }

    @Test
    public void saveRecord_opensDashboard() {
        Record record = createRecord();
        when(mDataSource.getPatientId()).thenReturn(PATIENT_ID);

        mPresenter.saveRecord(record);

        verify(mDataSource).setRecord(PATIENT_ID, record);
        verify(mView).openDashboard();
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
        return record;
    }
}
