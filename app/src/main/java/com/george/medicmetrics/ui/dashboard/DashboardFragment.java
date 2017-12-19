package com.george.medicmetrics.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.george.medicmetrics.R;
import com.george.medicmetrics.objects.Tile;
import com.george.medicmetrics.ui.anamnesis.AnamnesisActivity;
import com.george.medicmetrics.ui.base.BaseFragment;
import com.george.medicmetrics.ui.scan.ScanDeviceActivity;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends BaseFragment<DashboardContract.Presenter> implements DashboardContract.View {

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @NonNull
    @Override
    protected DashboardContract.Presenter createPresenter() {
        return new DashboardPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
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
        List<Tile> tileList = new ArrayList<>();
        tileList.add(measurementTile);
        tileList.add(anamnesisTile);
        tileList.add(aboutTile);
        return tileList;
    }

    @Override
    public void openScan() {
        Intent intent = ScanDeviceActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void openAnamnesis() {
        Intent intent = AnamnesisActivity.newIntent(getContext(), 1);
        startActivity(intent);
    }
}
