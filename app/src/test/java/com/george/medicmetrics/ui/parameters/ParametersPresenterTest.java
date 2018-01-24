package com.george.medicmetrics.ui.parameters;

import com.george.medicmetrics.objects.Record;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ParametersPresenterTest {

    private static final String EMPTY = "";
    private static final String CONSCIOUSNESS_LEVEL_ALERT = "alert";
    private static final String RESPIRATORY_RATE = "12";
    private static final String MILLILITER_PER_HOUR = "1";

    private ParametersPresenter mPresenter;

    @Mock
    private ParametersContract.View mView;

    @Before
    public void setupPresenter() {
        mPresenter = new ParametersPresenter();
        mPresenter.attachView(mView);
    }

    @Test
    public void calculateScore_showsInvalidRespiratoryRate_whenRespiratoryRateIsEmpty() {
        Record record = new Record();

        mPresenter.calculateScore(record, EMPTY, false, EMPTY, false, CONSCIOUSNESS_LEVEL_ALERT);

        verify(mView).showInvalidRespiratoryRate();
        verify(mView, never()).openScore(record);
    }

    @Test
    public void calculateScore_showsInvalidMilliliterPerHour_whenCatheterUsedAndMilliliterPerHourIsEmpty() {
        Record record = new Record();

        mPresenter.calculateScore(record, RESPIRATORY_RATE, true, EMPTY, false, CONSCIOUSNESS_LEVEL_ALERT);

        verify(mView).showInvalidMilliliterPerHour();
        verify(mView, never()).openScore(record);
    }

    @Test
    public void calculateScore_returnsClinicalConcernLow_whenScoreIsBelow4() {
        Record record = new Record();
        record.setBloodOxygen(96);
        record.setBodyTemperature(36.1);
        record.setSystolicBloodPressure(111);
        record.setHearRate(51);

        mPresenter.calculateScore(record, RESPIRATORY_RATE, false, MILLILITER_PER_HOUR, false, CONSCIOUSNESS_LEVEL_ALERT);

        verify(mView).openScore(record);
        assertTrue(record.getScore() == 0);
        assertTrue(record.getClinicalConcern() == Record.CLINICAL_CONCERN_LOW);
    }

    @Test
    public void calculateScore_returnsClinicalConcernMedium_whenScoreIsBelow4AndRespiratoryRateScoreIs3() {
        Record record = new Record();
        record.setBloodOxygen(96);
        record.setBodyTemperature(36.1);
        record.setSystolicBloodPressure(111);
        record.setHearRate(51);

        mPresenter.calculateScore(record, "8", false, MILLILITER_PER_HOUR, false, CONSCIOUSNESS_LEVEL_ALERT);

        verify(mView).openScore(record);
        assertTrue(record.getScore() == 3);
        assertTrue(record.getClinicalConcern() == Record.CLINICAL_CONCERN_MEDIUM);
    }

    @Test
    public void calculateScore_returnsClinicalConcernMedium_whenScoreIsBelow4AndUrineWarning() {
        Record record = new Record();
        record.setBloodOxygen(96);
        record.setBodyTemperature(36.1);
        record.setSystolicBloodPressure(111);
        record.setHearRate(51);

        mPresenter.calculateScore(record, RESPIRATORY_RATE, true, "30", false, CONSCIOUSNESS_LEVEL_ALERT);

        verify(mView).openScore(record);
        assertTrue(record.getScore() == 0);
        assertTrue(record.getClinicalConcern() == Record.CLINICAL_CONCERN_MEDIUM);
    }

    @Test
    public void calculateScore_returnsClinicalConcernMedium_whenScoreIsBetween5And6() {
        Record record = new Record();
        record.setBloodOxygen(92);
        record.setBodyTemperature(39.1);
        record.setSystolicBloodPressure(101);
        record.setHearRate(51);

        mPresenter.calculateScore(record, RESPIRATORY_RATE, false, MILLILITER_PER_HOUR, false, CONSCIOUSNESS_LEVEL_ALERT);

        verify(mView).openScore(record);
        assertTrue(record.getScore() == 5);
        assertTrue(record.getClinicalConcern() == Record.CLINICAL_CONCERN_MEDIUM);
    }

    @Test
    public void calculateScore_returnsClinicalConcernHigh_whenScoreIsAbove7() {
        Record record = new Record();
        record.setBloodOxygen(92);
        record.setBodyTemperature(39.1);
        record.setSystolicBloodPressure(101);
        record.setHearRate(131);

        mPresenter.calculateScore(record, RESPIRATORY_RATE, false, MILLILITER_PER_HOUR, false, CONSCIOUSNESS_LEVEL_ALERT);

        verify(mView).openScore(record);
        assertTrue(record.getScore() == 8);
        assertTrue(record.getClinicalConcern() == Record.CLINICAL_CONCERN_HIGH);
    }
}
