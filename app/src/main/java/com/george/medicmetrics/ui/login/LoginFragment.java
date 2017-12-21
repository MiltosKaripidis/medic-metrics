package com.george.medicmetrics.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.george.medicmetrics.R;
import com.george.medicmetrics.data.DataSource;
import com.george.medicmetrics.injection.Injection;
import com.george.medicmetrics.ui.base.BaseFragment;
import com.george.medicmetrics.ui.dashboard.DashboardActivity;

public class LoginFragment extends BaseFragment<LoginContract.Presenter> implements LoginContract.View {

    private TextInputEditText mUsernameEditText;
    private TextInputEditText mPasswordEditText;

    @NonNull
    @Override
    protected LoginContract.Presenter createPresenter() {
        DataSource dataSource = Injection.provideDataSource(getContext());
        return new LoginPresenter(dataSource);
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mUsernameEditText = view.findViewById(R.id.username_edit_text);
        mPasswordEditText = view.findViewById(R.id.password_edit_text);
        setupLoginButton(view);
        return view;
    }

    private void setupLoginButton(View view) {
        AppCompatButton loginButton = view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsernameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                mPresenter.login(username, password);
            }
        });
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
    public void showInvalidUser() {
        View view = getView();
        if (view == null) {
            return;
        }
        Snackbar.make(view, R.string.invalid_user, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void openDashboard() {
        Intent intent = DashboardActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void openRegister() {
        // TODO: Implement
    }

    @Override
    public void finish() {
        getActivity().finish();
    }
}
