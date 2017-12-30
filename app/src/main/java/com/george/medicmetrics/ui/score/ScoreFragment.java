package com.george.medicmetrics.ui.score;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.george.medicmetrics.R;
import com.george.medicmetrics.data.DataSource;
import com.george.medicmetrics.injection.Injection;
import com.george.medicmetrics.objects.Record;
import com.george.medicmetrics.ui.base.BaseFragment;
import com.george.medicmetrics.ui.dashboard.DashboardActivity;

public class ScoreFragment extends BaseFragment<ScoreContract.Presenter> implements ScoreContract.View {

    private static final String ARG_RECORD = "record";
    private CardView mCardView;
    private TextView mRespiratoryRateTextView;
    private TextView mBloodOxygenTextView;
    private TextView mBodyTemperatureTextView;
    private TextView mSystolicBloodPressureTextView;
    private TextView mHeartRateTextView;
    private TextView mUrineOutputTextView;
    private TextView mOxygenSupplementedTextView;
    private TextView mConsciousnessLevelTextView;
    private TextView mDescriptionTextView;
    private TextView mScoreTextView;
    private TextView mTimestampTextView;

    public static ScoreFragment newInstance(Record record) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_RECORD, record);

        ScoreFragment fragment = new ScoreFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    protected ScoreContract.Presenter createPresenter() {
        DataSource dataSource = Injection.provideDataSource(getContext());
        return new ScorePresenter(dataSource);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score, container, false);
        mCardView = view.findViewById(R.id.card_view);
        mRespiratoryRateTextView = view.findViewById(R.id.respiratory_rate_text_view);
        mBloodOxygenTextView = view.findViewById(R.id.blood_oxygen_text_view);
        mBodyTemperatureTextView = view.findViewById(R.id.body_temperature_text_view);
        mSystolicBloodPressureTextView = view.findViewById(R.id.systolic_blood_pressure_text_view);
        mHeartRateTextView = view.findViewById(R.id.heart_rate_text_view);
        mUrineOutputTextView = view.findViewById(R.id.urine_text_view);
        mOxygenSupplementedTextView = view.findViewById(R.id.oxygen_supplemented_text_view);
        mConsciousnessLevelTextView = view.findViewById(R.id.consciousness_level_text_view);
        mDescriptionTextView = view.findViewById(R.id.description_text_view);
        mScoreTextView = view.findViewById(R.id.score_text_view);
        mTimestampTextView = view.findViewById(R.id.timestamp_text_view);
        setupDiscardButton(view);
        setupSaveButton(view);
        return view;
    }

    private void setupDiscardButton(@NonNull View view) {
        AppCompatButton discardButton = view.findViewById(R.id.discard_button);
        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDashboard();
            }
        });
    }

    private void setupSaveButton(@NonNull View view) {
        AppCompatButton saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Record record = (Record) getArguments().getSerializable(ARG_RECORD);
                mPresenter.saveRecord(record);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Record record = (Record) getArguments().getSerializable(ARG_RECORD);
        mPresenter.loadScore(record);
    }

    @Override
    public void changeCardColor(int colorId) {
        int color = getResources().getColor(colorId);
        mCardView.setCardBackgroundColor(color);
    }

    @Override
    public void showRespiratoryRate(@NonNull String bpm) {
        String message = getString(R.string.format_bpm, bpm);
        mRespiratoryRateTextView.setText(message);
    }

    @Override
    public void showBloodOxygen(@NonNull String percent) {
        String message = getString(R.string.format_percent, percent);
        mBloodOxygenTextView.setText(message);
    }

    @Override
    public void showBodyTemperature(@NonNull String celsius) {
        String message = getString(R.string.format_celsius, celsius);
        mBodyTemperatureTextView.setText(message);
    }

    @Override
    public void showSystolicBloodPressure(@NonNull String pressure) {
        mSystolicBloodPressureTextView.setText(pressure);
    }

    @Override
    public void showHeartRate(@NonNull String bpm) {
        String message = getString(R.string.format_bpm, bpm);
        mHeartRateTextView.setText(message);
    }

    @Override
    public void showUrineOutput(@NonNull String milliliterPerHour) {
        String message = getString(R.string.format_milliliter_per_hour, milliliterPerHour);
        mUrineOutputTextView.setText(message);
    }

    @Override
    public void showOxygenSupplemented(@NonNull String oxygenSupplemented) {
        mOxygenSupplementedTextView.setText(oxygenSupplemented);
    }

    @Override
    public void showConsciousnessLevel(@NonNull String consciousnessLevel) {
        mConsciousnessLevelTextView.setText(consciousnessLevel);
    }

    @Override
    public void showDescription(@StringRes int description) {
        String message = getString(description);
        mDescriptionTextView.setText(message);
    }

    @Override
    public void showScore(@NonNull String score) {
        mScoreTextView.setText(score);
    }

    @Override
    public void showTimestamp(@NonNull String timestamp) {
        mTimestampTextView.setText(timestamp);
    }

    @Override
    public void openDashboard() {
        Intent intent = DashboardActivity.newIntent(getContext());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
