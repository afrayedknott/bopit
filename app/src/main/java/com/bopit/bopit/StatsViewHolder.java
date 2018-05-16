package com.bopit.bopit;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class StatsViewHolder extends RecyclerView.ViewHolder {

        public TextView highScorerNameTextView, highScoreTextView;

        public StatsViewHolder(View highScoreEntryView) {

            super(highScoreEntryView);

            highScorerNameTextView = highScoreEntryView.findViewById(R.id.high_scorer_name);
            highScoreTextView = highScoreEntryView.findViewById(R.id.mean_reaction_time);

        }

}
