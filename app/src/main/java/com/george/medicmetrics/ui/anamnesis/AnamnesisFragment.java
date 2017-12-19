package com.george.medicmetrics.ui.anamnesis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.george.medicmetrics.R;
import com.george.medicmetrics.data.DataSource;
import com.george.medicmetrics.injection.Injection;
import com.george.medicmetrics.objects.Record;
import com.george.medicmetrics.ui.base.BaseFragment;

import java.util.List;

public class AnamnesisFragment extends BaseFragment<AnamnesisContract.Presenter> implements AnamnesisContract.View {

    private static final String ARG_PATIENT_ID = "patient_id";
    private AnamnesisAdapter mAnamnesisAdapter;
    private TextView mEmptyTextView;

    public static AnamnesisFragment newInstance(int patientId) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_PATIENT_ID, patientId);

        AnamnesisFragment fragment = new AnamnesisFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    protected AnamnesisContract.Presenter createPresenter() {
        DataSource dataSource = Injection.provideDataSource(getContext());
        return new AnamnesisPresenter(dataSource);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anamnesis, container, false);
        setupToolbar(view);
        setupRecyclerView(view);
        mEmptyTextView = view.findViewById(R.id.empty_text_view);
        return view;
    }

    private void setupToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(getActivity());
            }
        });
    }

    private void setupRecyclerView(View view) {
        mAnamnesisAdapter = new AnamnesisAdapter(null, new AnamnesisAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int recordId) {
                // TODO: Open record
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAnamnesisAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int patientId = getArguments().getInt(ARG_PATIENT_ID);
        mPresenter.loadAnamnesis(patientId);
    }

    @Override
    public void showAnamnesis(@NonNull List<Record> recordList) {
        mAnamnesisAdapter.setRecordList(recordList);
        mAnamnesisAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyAnamnesis() {
        mEmptyTextView.setVisibility(View.VISIBLE);
    }
}
