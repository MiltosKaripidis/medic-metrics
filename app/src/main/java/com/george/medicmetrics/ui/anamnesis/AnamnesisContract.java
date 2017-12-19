package com.george.medicmetrics.ui.anamnesis;

import android.support.annotation.NonNull;

import com.george.medicmetrics.objects.Record;
import com.george.medicmetrics.ui.base.BaseContract;

import java.util.List;

interface AnamnesisContract {

    interface View extends BaseContract.View {

        void showAnamnesis(@NonNull List<Record> recordList);

        void showEmptyAnamnesis();
    }

    interface Presenter extends BaseContract.Presenter<View> {

        void loadAnamnesis(int patientId);
    }
}
