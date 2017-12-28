package com.george.medicmetrics.ui.parameters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;

import com.george.medicmetrics.R;
import com.george.medicmetrics.objects.Record;
import com.george.medicmetrics.ui.base.BaseFragment;
import com.george.medicmetrics.ui.score.ScoreActivity;

public class ParametersFragment extends BaseFragment<ParametersContract.Presenter> implements ParametersContract.View {

    private static final String ARG_RECORD = "record";
    private TextInputEditText mRespiratoryRateEditText;
    private SwitchCompat mCatheterSwitch;
    private TextInputLayout mCatheterTextInputLayout;
    private TextInputEditText mCatheterEditText;
    private SwitchCompat mOxygenSwitch;
    private String mConsciousnessLevel;

    public static ParametersFragment newInstance(@NonNull Record record) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_RECORD, record);

        ParametersFragment fragment = new ParametersFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    protected ParametersContract.Presenter createPresenter() {
        return new ParametersPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parameters, container, false);

        mRespiratoryRateEditText = view.findViewById(R.id.respiratory_rate_edit_text);
        mCatheterTextInputLayout = view.findViewById(R.id.catheter_text_input_layout);
        mCatheterEditText = view.findViewById(R.id.catheter_edit_text);
        mOxygenSwitch = view.findViewById(R.id.oxygen_switch);
        setupCatheterSwitch(view);
        setupConsciousnessLevelSpinner(view);
        setupCalculateScoreButton(view);

        return view;
    }

    private void setupCatheterSwitch(@NonNull View view) {
        mCatheterSwitch = view.findViewById(R.id.catheter_switch);
        mCatheterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCatheterTextInputLayout.setVisibility(View.VISIBLE);
                } else {
                    mCatheterTextInputLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setupConsciousnessLevelSpinner(@NonNull View view) {
        AppCompatSpinner spinner = view.findViewById(R.id.consciousness_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mConsciousnessLevel = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void setupCalculateScoreButton(@NonNull View view) {
        AppCompatButton calculateScoreButton = view.findViewById(R.id.calculate_score_button);
        calculateScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Record record = (Record) getArguments().getSerializable(ARG_RECORD);
                String respiratoryRate = mRespiratoryRateEditText.getText().toString();
                boolean catheterUsed = mCatheterSwitch.isChecked();
                String milliliterPerHour = mCatheterEditText.getText().toString();
                boolean oxygenSupplemented = mOxygenSwitch.isChecked();

                mPresenter.calculateScore(record, respiratoryRate, catheterUsed, milliliterPerHour, oxygenSupplemented, mConsciousnessLevel);
            }
        });
    }

    @Override
    public void showInvalidRespiratoryRate() {
        mRespiratoryRateEditText.setError(getString(R.string.invalid_respiratory_rate));
    }

    @Override
    public void showInvalidMilliliterPerHour() {
        mCatheterEditText.setError(getString(R.string.invalid_milliliter_per_hour));
    }

    @Override
    public void openScore(@NonNull Record record) {
        Intent intent = ScoreActivity.newIntent(getContext(), record);
        startActivity(intent);
    }
}
