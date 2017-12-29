package com.george.medicmetrics.ui.anamnesis;

import android.support.annotation.NonNull;

import com.george.medicmetrics.data.Callback;
import com.george.medicmetrics.data.DataSource;
import com.george.medicmetrics.objects.Record;
import com.george.medicmetrics.ui.base.BasePresenter;

import java.util.List;

class AnamnesisPresenter extends BasePresenter<AnamnesisContract.View> implements AnamnesisContract.Presenter {

    private DataSource mDataSource;

    AnamnesisPresenter(DataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    public void loadAnamnesis() {
        int patientId = mDataSource.getPatientId();
        mDataSource.getRecordList(patientId, new Callback<List<Record>>() {
            @Override
            public void onSuccess(@NonNull List<Record> recordList) {
                if (recordList.isEmpty()) {
                    mView.showEmptyAnamnesis();
                    return;
                }

                mView.showAnamnesis(recordList);
            }

            @Override
            public void onFailure() {
                mView.showEmptyAnamnesis();
            }
        });
    }
}
