package com.george.medicmetrics.ui.dashboard;

import com.george.medicmetrics.data.DataSource;
import com.george.medicmetrics.objects.Patient;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DashboardPresenterTest {

    private static final int PATIENT_ID = 1;
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String EXPECTED_FULL_NAME = "last_name first_name";
    private static final int NEW_MEASUREMENT_ID = 1;
    private static final int ANAMNESIS_ID = 2;
    private static final int ABOUT_ID = 3;
    private static final int UNSUBSCRIBE_ID = 4;
    private static final int LOGOUT_ID = 5;

    private DashboardPresenter mPresenter;

    @Mock
    private DataSource mDataSource;

    @Mock
    private DashboardContract.View mView;

    @Before
    public void setupPresenter() {
        mPresenter = new DashboardPresenter(mDataSource);
        mPresenter.attachView(mView);
    }

    @Test
    public void loadUser_showsFullName() {
        when(mDataSource.getPatientId()).thenReturn(PATIENT_ID);
        when(mDataSource.getPatient(PATIENT_ID)).thenReturn(createPatient());

        mPresenter.loadUser();

        verify(mView).showFullName(EXPECTED_FULL_NAME);
    }

    @Test
    public void deletePatient_opensLogin() {
        when(mDataSource.getPatientId()).thenReturn(PATIENT_ID);

        mPresenter.deletePatient();

        verify(mDataSource).deletePatient(PATIENT_ID);
        verify(mView).openLogin();
        verify(mView).finish();
    }

    @Test
    public void handleClick_opensScan_whenNewMeasurementId() {
        mPresenter.handleClick(NEW_MEASUREMENT_ID);

        verify(mView).openScan();
    }

    @Test
    public void handleClick_opensAnamnesis_whenAnamnesisId() {
        mPresenter.handleClick(ANAMNESIS_ID);

        verify(mView).openAnamnesis();
    }

    @Test
    public void handleClick_opensAbout_whenAboutId() {
        mPresenter.handleClick(ABOUT_ID);

        verify(mView).openAbout();
    }

    @Test
    public void handleClick_opensDialog_whenUnsubscribeId() {
        mPresenter.handleClick(UNSUBSCRIBE_ID);

        verify(mView).openDialog();
    }

    @Test
    public void handleClick_opensLogin_whenLogoutId() {
        mPresenter.handleClick(LOGOUT_ID);

        verify(mDataSource).setPatientId(-1);
        verify(mView).openLogin();
        verify(mView).finish();
    }

    private Patient createPatient() {
        Patient patient = new Patient();
        patient.setId(PATIENT_ID);
        patient.setName(FIRST_NAME);
        patient.setLastName(LAST_NAME);
        return patient;
    }
}
