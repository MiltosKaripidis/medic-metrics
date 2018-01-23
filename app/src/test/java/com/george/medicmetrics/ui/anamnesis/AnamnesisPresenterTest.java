package com.george.medicmetrics.ui.anamnesis;

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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnamnesisPresenterTest {

    private static final int PATIENT_ID = 1;
    private static final List<Record> EMPTY_RECORD_LIST = new ArrayList<>();
    private AnamnesisPresenter mPresenter;

    @Mock
    private AnamnesisContract.View mView;

    @Mock
    private DataSource mDataSource;

    @Captor
    private ArgumentCaptor<Callback<List<Record>>> mCallbackArgumentCaptor;

    @Before
    public void setupPresenter() {
        mPresenter = new AnamnesisPresenter(mDataSource);
        mPresenter.attachView(mView);
    }

    @Test
    public void loadAnamnesis_showsEmptyAnamnesis_whenFailed() {
        when(mDataSource.getPatientId()).thenReturn(PATIENT_ID);

        mPresenter.loadAnamnesis();

        verify(mDataSource).getRecordList(anyInt(), mCallbackArgumentCaptor.capture());
        mCallbackArgumentCaptor.getValue().onFailure();
        verify(mView).showEmptyAnamnesis();
    }

    @Test
    public void loadAnamnesis_showsEmptyAnamnesis_whenEmpty() {
        when(mDataSource.getPatientId()).thenReturn(PATIENT_ID);

        mPresenter.loadAnamnesis();

        verify(mDataSource).getRecordList(anyInt(), mCallbackArgumentCaptor.capture());
        mCallbackArgumentCaptor.getValue().onSuccess(EMPTY_RECORD_LIST);
        verify(mView).showEmptyAnamnesis();
    }

    @Test
    public void loadAnamnesis_showsAnamnesis() {
        List<Record> recordList = createRecordList();
        when(mDataSource.getPatientId()).thenReturn(PATIENT_ID);

        mPresenter.loadAnamnesis();

        verify(mDataSource).getRecordList(anyInt(), mCallbackArgumentCaptor.capture());
        mCallbackArgumentCaptor.getValue().onSuccess(recordList);
        verify(mView).showAnamnesis(recordList);
    }

    private List<Record> createRecordList() {
        Record record = new Record();
        List<Record> recordList = new ArrayList<>();
        recordList.add(record);
        return recordList;
    }
}
