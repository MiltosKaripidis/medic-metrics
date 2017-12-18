package com.george.medicmetrics.ui.dashboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.george.medicmetrics.R;
import com.george.medicmetrics.objects.Tile;

import java.util.List;

class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.TileHolder> {

    private static final int VIEW_TYPE_ODD = 1;
    private static final int VIEW_TYPE_EVEN = 2;
    private List<Tile> mTileList;
    private OnItemClickListener mOnItemClickListener;

    DashboardAdapter(List<Tile> tileList, OnItemClickListener onItemClickListener) {
        mTileList = tileList;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public TileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;

        switch (viewType) {
            case VIEW_TYPE_EVEN:
                view = LayoutInflater.from(context).inflate(R.layout.row_fragment_dashboard_even, parent, false);
                return new TileHolder(view);
            case VIEW_TYPE_ODD:
                view = LayoutInflater.from(context).inflate(R.layout.row_fragment_dashboard_odd, parent, false);
                return new TileHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(TileHolder holder, int position) {
        final Tile tile = mTileList.get(position);

        holder.imageView.setImageResource(tile.getImageId());
        holder.textView.setText(tile.getText());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClicked(tile.getId());
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? VIEW_TYPE_EVEN : VIEW_TYPE_ODD;
    }

    @Override
    public int getItemCount() {
        return mTileList == null ? 0 : mTileList.size();
    }

    public interface OnItemClickListener {

        void onItemClicked(int id);
    }

    static class TileHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        TileHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view);
            textView = itemView.findViewById(R.id.text_view);
        }
    }
}
