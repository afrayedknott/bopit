package com.bopit.bopit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class StatsRecyclerViewAdapter extends RecyclerView.Adapter<StatsViewHolder> {

    private ArrayList<StatsEntry> statsEntryArrayList;
    private Context context;

    public StatsRecyclerViewAdapter(Context context, ArrayList<StatsEntry> statsEntryArrayList) {

        this.statsEntryArrayList = statsEntryArrayList;

    }

    @Override
    public StatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_stats_row, parent, false);

        return new StatsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(StatsViewHolder holder, int position) {

        StatsEntry statsEntry = statsEntryArrayList.get(position);
        holder.highScorerNameTextView.setText(statsEntry.getUsername());
        holder.highScoreTextView.setText(Long.toString(statsEntry.getMeanReactionTime()));

    }

    @Override
    public int getItemCount() {
        return statsEntryArrayList.size();
    }

}