package com.george.medicmetrics.ui.dashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.george.medicmetrics.R;
import com.george.medicmetrics.data.DataSource;
import com.george.medicmetrics.injection.Injection;
import com.george.medicmetrics.objects.Tile;
import com.george.medicmetrics.ui.about.AboutActivity;
import com.george.medicmetrics.ui.anamnesis.AnamnesisActivity;
import com.george.medicmetrics.ui.base.BaseFragment;
import com.george.medicmetrics.ui.login.LoginActivity;
import com.george.medicmetrics.ui.scan.ScanDeviceActivity;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends BaseFragment<DashboardContract.Presenter> implements DashboardContract.View {

    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @NonNull
    @Override
    protected DashboardContract.Presenter createPresenter() {
        DataSource dataSource = Injection.provideDataSource(getContext());
        return new DashboardPresenter(dataSource);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mCollapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar);
        setupRecyclerView(view);
        return view;
    }

    private void setupRecyclerView(View view) {
        DashboardAdapter dashboardAdapter = new DashboardAdapter(getTileList(), new DashboardAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int id) {
                mPresenter.handleClick(id);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(dashboardAdapter);
    }

    private List<Tile> getTileList() {
        Tile measurementTile = new Tile(1, R.drawable.examine, "New Measurement");
        Tile anamnesisTile = new Tile(2, R.drawable.anamnesis, "Anamnesis");
        Tile aboutTile = new Tile(3, R.drawable.about, "About");
        Tile unsubscribeTile = new Tile(4, R.drawable.unsubscribe, "Delete Patient");
        Tile logoutTile = new Tile(5, R.drawable.logout, "Logout");
        List<Tile> tileList = new ArrayList<>();
        tileList.add(measurementTile);
        tileList.add(anamnesisTile);
        tileList.add(aboutTile);
        tileList.add(unsubscribeTile);
        tileList.add(logoutTile);
        return tileList;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.loadUser();
    }

    @Override
    public void showFullName(@NonNull String fullName) {
        mCollapsingToolbarLayout.setTitle(fullName);
    }

    @Override
    public void openScan() {
        Intent intent = ScanDeviceActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void openAnamnesis() {
        Intent intent = AnamnesisActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void openLogin() {
        Intent intent = LoginActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void openAbout() {
        Intent intent = AboutActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void openDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Patient")
                .setMessage("Are you sure you want to delete the current patient?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                    }
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deletePatient();
                    }
                })
                .show();
    }

    @Override
    public void finish() {
        getActivity().finish();
    }
}
