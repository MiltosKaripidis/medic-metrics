package com.george.medicmetrics.ui.report;

import com.george.medicmetrics.data.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReportPresenterTest {

    private ReportPresenter mPresenter;

    @Mock
    private DataSource mDataSource;

    @Mock
    private ReportContract.View mView;

    @Before
    public void setupPresenter() {
        mPresenter = new ReportPresenter(mDataSource);
        mPresenter.attachView(mView);
    }

    @Test
    public void loadRecord_() {

    }
}
