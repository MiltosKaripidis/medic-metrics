package com.george.medicmetrics.ui.register;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.george.medicmetrics.R;
import com.george.medicmetrics.data.DataSource;
import com.george.medicmetrics.data.Injection;
import com.george.medicmetrics.ui.base.BaseFragment;

public class RegisterFragment extends BaseFragment<RegisterContract.Presenter> implements RegisterContract.View {

    private TextInputEditText mNameEditText;
    private TextInputEditText mLastNameEditText;
    private TextInputEditText mUsernameEditText;
    private TextInputEditText mPasswordEditText;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @NonNull
    @Override
    protected RegisterContract.Presenter createPresenter() {
        DataSource dataSource = Injection.provideDataSource(getContext());
        return new RegisterPresenter(dataSource);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mNameEditText = view.findViewById(R.id.name_edit_text);
        mLastNameEditText = view.findViewById(R.id.last_name_edit_text);
        mUsernameEditText = view.findViewById(R.id.username_edit_text);
        mPasswordEditText = view.findViewById(R.id.password_edit_text);
        setupRegisterButton(view);
        return view;
    }

    private void setupRegisterButton(View view) {
        AppCompatButton registerButton = view.findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameEditText.getText().toString();
                String lastName = mLastNameEditText.getText().toString();
                String username = mUsernameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                mPresenter.register(name, lastName, username, password);
            }
        });
    }

    @Override
    public void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view == null) return;

        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void showInvalidName() {
        mNameEditText.setError(getString(R.string.invalid_name));
    }

    @Override
    public void showInvalidLastName() {
        mLastNameEditText.setError(getString(R.string.invalid_last_name));
    }

    @Override
    public void showInvalidUsername() {
        mUsernameEditText.setError(getString(R.string.invalid_username));
    }

    @Override
    public void showInvalidPassword() {
        mPasswordEditText.setError(getString(R.string.invalid_password));
    }

    @Override
    public void showUserExists() {
        if (getView() == null) return;
        Snackbar.make(getView(), "User already exists", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showRegisterSuccess() {
        getActivity().setResult(Activity.RESULT_OK);
    }

    @Override
    public void finish() {
        getActivity().finish();
    }
}
