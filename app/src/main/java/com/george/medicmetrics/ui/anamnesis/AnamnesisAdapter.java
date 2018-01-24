package com.george.medicmetrics.ui.anamnesis;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.george.medicmetrics.R;
import com.george.medicmetrics.objects.Record;

import java.util.List;

class AnamnesisAdapter extends RecyclerView.Adapter<AnamnesisAdapter.RecordHolder> {

    private List<Record> mRecordList;
    private OnItemClickListener mOnItemClickListener;

    AnamnesisAdapter(List<Record> recordList, OnItemClickListener onItemClickListener) {
        mRecordList = recordList;
        mOnItemClickListener = onItemClickListener;
    }

    void setRecordList(List<Record> recordList) {
        mRecordList = recordList;
    }

    @Override
    public RecordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_fragment_anamnesis, parent, false);
        return new RecordHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordHolder holder, int position) {
        final Record record = mRecordList.get(position);
        String score = String.valueOf(record.getScore());
        Context context = holder.itemView.getContext();
        int color = context.getResources().getColor(record.getColor());
        holder.cardView.setCardBackgroundColor(color);
        holder.scoreTextView.setText(score);
        holder.timestampTextView.setText(record.getTimestamp());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClicked(record.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecordList == null ? 0 : mRecordList.size();
    }

    interface OnItemClickListener {

        void onItemClicked(int recordId);
    }

    static class RecordHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView scoreTextView;
        private TextView timestampTextView;

        RecordHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_view);
            scoreTextView = itemView.findViewById(R.id.score_text_view);
            timestampTextView = itemView.findViewById(R.id.timestamp_text_view);
        }
    }
}
