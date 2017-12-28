package com.george.medicmetrics.ui.parameters;

import android.support.annotation.NonNull;

import com.george.medicmetrics.objects.Record;
import com.george.medicmetrics.ui.base.BasePresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class ParametersPresenter extends BasePresenter<ParametersContract.View> implements ParametersContract.Presenter {

    @Override
    public void calculateScore(@NonNull Record record,
                               @NonNull String respiratoryRate,
                               boolean catheterUsed,
                               @NonNull String milliliterPerHour,
                               boolean oxygenSupplemented,
                               @NonNull String consciousnessLevel) {
        if (respiratoryRate.isEmpty()) {
            mView.showInvalidRespiratoryRate();
            return;
        }

        record.setRespiratoryRate(Double.valueOf(respiratoryRate));
        record.setCatheterUsed(catheterUsed);
        if (catheterUsed) {
            if (milliliterPerHour.isEmpty()) {
                mView.showInvalidMilliliterPerHour();
                return;
            }
            record.setMilliliterPerHour(Integer.valueOf(milliliterPerHour));
        }
        record.setOxygenSupplemented(oxygenSupplemented);
        record.setConsciousnessLevel(consciousnessLevel);
        record.setScore(calculateScore(record));
        record.setClinicalConcern(calculateClinicalConcern(record));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault());
        String timestamp = simpleDateFormat.format(new Date());
        record.setTimestamp(timestamp);

        mView.openScore(record);
    }

    private int calculateScore(@NonNull Record record) {
        int respiratoryRateScore = calculateRespiratoryRate(record.getRespiratoryRate());
        int bloodOxygenScore = calculateBloodOxygen(record.getBloodOxygen());
        int bodyTemperatureScore = calculateBodyTemperature(record.getBodyTemperature());
        int systolicBloodPressureScore = calculateSystolicBloodPressure(record.getSystolicBloodPressure());
        int heartRateScore = calculateHeartRate(record.getHearRate());
        int consciousnessLevelScore = calculateConsciousnessLevel(record.getConsciousnessLevel());
        int supplementedOxygenScore = calculateSupplementedOxygen(record.isOxygenSupplemented());

        return respiratoryRateScore + bloodOxygenScore + bodyTemperatureScore +
                systolicBloodPressureScore + heartRateScore + consciousnessLevelScore +
                supplementedOxygenScore;
    }

    @Record.ClinicalConcern
    private int calculateClinicalConcern(@NonNull Record record) {
        int respiratoryRateScore = calculateRespiratoryRate(record.getRespiratoryRate());
        int bloodOxygenScore = calculateBloodOxygen(record.getBloodOxygen());
        int bodyTemperatureScore = calculateBodyTemperature(record.getBodyTemperature());
        int systolicBloodPressureScore = calculateSystolicBloodPressure(record.getSystolicBloodPressure());
        int heartRateScore = calculateHeartRate(record.getHearRate());
        boolean urineWarning = calculateCatheterUsed(record.isCatheterUsed(), record.getMilliliterPerHour());
        int score = record.getScore();

        if (score <= 4) {
            if (respiratoryRateScore == 3 || bloodOxygenScore == 3 || bodyTemperatureScore == 3 ||
                    systolicBloodPressureScore == 3 || heartRateScore == 3) {
                return Record.CLINICAL_CONCERN_MEDIUM;
            } else if (urineWarning) {
                return Record.CLINICAL_CONCERN_MEDIUM;
            } else {
                return Record.CLINICAL_CONCERN_LOW;
            }
        } else if (score >= 5 && score <= 6) {
            return Record.CLINICAL_CONCERN_MEDIUM;
        } else if (score >= 7) {
            return Record.CLINICAL_CONCERN_HIGH;
        } else {
            return Record.CLINICAL_CONCERN_MEDIUM;
        }
    }

    private int calculateRespiratoryRate(double respiratoryRate) {
        if (respiratoryRate <= 8) {
            return 3;
        } else if (respiratoryRate >= 9 && respiratoryRate <= 11) {
            return 1;
        } else if (respiratoryRate >= 12 && respiratoryRate <= 20) {
            return 0;
        } else if (respiratoryRate >= 21 && respiratoryRate <= 24) {
            return 2;
        } else if (respiratoryRate >= 25) {
            return 3;
        } else {
            return 0;
        }
    }

    private int calculateBloodOxygen(double bloodOxygen) {
        if (bloodOxygen <= 91) {
            return 3;
        } else if (bloodOxygen >= 92 && bloodOxygen <= 93) {
            return 2;
        } else if (bloodOxygen >= 94 && bloodOxygen <= 95) {
            return 1;
        } else if (bloodOxygen >= 96) {
            return 0;
        } else {
            return 0;
        }
    }

    private int calculateBodyTemperature(double bodyTemperature) {
        if (bodyTemperature <= 35) {
            return 3;
        } else if (bodyTemperature >= 35.1 && bodyTemperature <= 36) {
            return 1;
        } else if (bodyTemperature >= 36.1 && bodyTemperature <= 38) {
            return 0;
        } else if (bodyTemperature >= 38.1 && bodyTemperature <= 39) {
            return 1;
        } else if (bodyTemperature >= 39.1) {
            return 2;
        } else {
            return 0;
        }
    }

    private int calculateSystolicBloodPressure(double bloodPressure) {
        if (bloodPressure <= 90) {
            return 3;
        } else if (bloodPressure >= 91 && bloodPressure <= 100) {
            return 2;
        } else if (bloodPressure >= 101 && bloodPressure <= 110) {
            return 1;
        } else if (bloodPressure >= 111 && bloodPressure <= 219) {
            return 0;
        } else if (bloodPressure >= 220) {
            return 3;
        } else {
            return 0;
        }
    }

    private int calculateHeartRate(double heartRate) {
        if (heartRate <= 40) {
            return 3;
        } else if (heartRate >= 41 && heartRate <= 50) {
            return 1;
        } else if (heartRate >= 51 && heartRate <= 90) {
            return 0;
        } else if (heartRate >= 91 && heartRate <= 110) {
            return 1;
        } else if (heartRate >= 111 && heartRate <= 130) {
            return 2;
        } else if (heartRate >= 131) {
            return 3;
        } else {
            return 0;
        }
    }

    private int calculateConsciousnessLevel(@NonNull String consciousnessLevel) {
        switch (consciousnessLevel) {
            case "Alert":
                return 0;
            case "Voice":
            case "Pain":
            case "Unresponsive":
                return 3;
            default:
                return 0;
        }
    }

    private int calculateSupplementedOxygen(boolean oxygenSupplemented) {
        return oxygenSupplemented ? 2 : 0;
    }

    private boolean calculateCatheterUsed(boolean catheterUsed, int milliliterPerHour) {
        return catheterUsed && milliliterPerHour >= 30;
    }
}
