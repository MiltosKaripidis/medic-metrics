package com.george.medicmetrics.ui.report;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.george.medicmetrics.R;
import com.george.medicmetrics.data.DataSource;
import com.george.medicmetrics.injection.Injection;
import com.george.medicmetrics.ui.base.BaseFragment;

public class ReportFragment extends BaseFragment<ReportContract.Presenter> implements ReportContract.View {

    private static final String ARG_RECORD_ID = "record_id";
    private CardView mCardView;
    private TextView mRespiratoryTextView;
    private TextView mBloodOxygenTextView;
    private TextView mBodyTemperatureTextView;
    private TextView mSystolicBloodPressureTextView;
    private TextView mHeartRateTextView;
    private TextView mScoreTextView;
    private TextView mTimestampTextView;

    public static ReportFragment newInstance(int recordId) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_RECORD_ID, recordId);

        ReportFragment fragment = new ReportFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    protected ReportContract.Presenter createPresenter() {
        DataSource dataSource = Injection.provideDataSource(getContext());
        return new ReportPresenter(dataSource);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        setupToolbar(view);
        mCardView = view.findViewById(R.id.card_view);
        mRespiratoryTextView = view.findViewById(R.id.respiratory_rate_text_view);
        mBloodOxygenTextView = view.findViewById(R.id.blood_oxygen_text_view);
        mBodyTemperatureTextView = view.findViewById(R.id.body_temperature_text_view);
        mSystolicBloodPressureTextView = view.findViewById(R.id.systolic_blood_pressure_text_view);
        mHeartRateTextView = view.findViewById(R.id.heart_rate_text_view);
        mScoreTextView = view.findViewById(R.id.score_text_view);
        mTimestampTextView = view.findViewById(R.id.timestamp_text_view);
        return view;
    }

    private void setupToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int recordId = getArguments().getInt(ARG_RECORD_ID);
        mPresenter.loadRecord(recordId);
    }

    @Override
    public void showRespiratoryRate(String bpm) {
        String message = getString(R.string.format_bpm, bpm);
        mRespiratoryTextView.setText(message);
    }

    @Override
    public void showBloodOxygen(String percent) {
        String message = getString(R.string.format_percent, percent);
        mBloodOxygenTextView.setText(message);
    }

    @Override
    public void showBodyTemperature(String celsius) {
        String message = getString(R.string.format_celsius, celsius);
        mBodyTemperatureTextView.setText(message);
    }

    @Override
    public void showSystolicBloodPressure(String pressure) {
        mSystolicBloodPressureTextView.setText(pressure);
    }

    @Override
    public void showHeartRate(String bpm) {
        String message = getString(R.string.format_bpm, bpm);
        mHeartRateTextView.setText(message);
    }

    @Override
    public void showScore(String score) {
        mScoreTextView.setText(score);
    }

    @Override
    public void showTimestamp(String timestamp) {
        mTimestampTextView.setText(timestamp);
    }

    @Override
    public void changeCardColor(int colorId) {
        int color = getResources().getColor(colorId);
        mCardView.setCardBackgroundColor(color);
    }
}
