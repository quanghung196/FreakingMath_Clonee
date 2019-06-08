package com.example.freakingmath_clone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class LeaderBoard_Adapter extends RecyclerView.Adapter<LeaderBoard_Adapter.ViewHolder> {

    private ArrayList<PlayerModel> playerModels;

    public LeaderBoard_Adapter(ArrayList<PlayerModel> playerModels) {
        this.playerModels = playerModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_player, viewGroup, false);
        LeaderBoard_Adapter.ViewHolder viewHolder = new LeaderBoard_Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final PlayerModel playerModel = playerModels.get(position);
        if (position < 3) {
            viewHolder.tvNumber.setText("");
        }
        viewHolder.tvPlayerName1.setText(playerModel.getPlayerName());
        viewHolder.tvPlayerScore1.setText(playerModel.getPlayerScore() + "");

        switch (position + 1) {
            case 1:
                viewHolder.tvPlayerName1.setTypeface(Typeface.DEFAULT_BOLD);
                viewHolder.tvPlayerScore1.setTypeface(Typeface.DEFAULT_BOLD);
                viewHolder.tvPlayerName1.setTextColor(Color.RED);
                viewHolder.tvPlayerScore1.setTextColor(Color.RED);
                viewHolder.tvPlayerScore1.setText(playerModel.getPlayerScore() + "");
                viewHolder.tvNumber.setBackgroundResource(R.drawable.icon_gold_medal_24);
                break;
            case 2:
                viewHolder.tvPlayerName1.setTypeface(Typeface.DEFAULT_BOLD);
                viewHolder.tvPlayerScore1.setTypeface(Typeface.DEFAULT_BOLD);
                viewHolder.tvPlayerName1.setTextColor(Color.YELLOW);
                viewHolder.tvPlayerScore1.setTextColor(Color.YELLOW);
                viewHolder.tvNumber.setBackgroundResource(R.drawable.icon_silver_medal_24);
                break;
            case 3:
                viewHolder.tvPlayerName1.setTypeface(Typeface.DEFAULT_BOLD);
                viewHolder.tvPlayerScore1.setTypeface(Typeface.DEFAULT_BOLD);
                viewHolder.tvPlayerName1.setTextColor(Color.BLUE);
                viewHolder.tvPlayerScore1.setTextColor(Color.BLUE);
                viewHolder.tvNumber.setBackgroundResource(R.drawable.icon_bronze_medal_24);
                break;
            default:
                viewHolder.tvNumber.setBackgroundResource(0);
                viewHolder.tvNumber.setText((position + 1) + "");
                break;
        }

    }

    @Override
    public int getItemCount() {
        return playerModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNumber;
        private TextView tvPlayerName1;
        private TextView tvPlayerScore1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvPlayerName1 = itemView.findViewById(R.id.tvPlayerName1);
            tvPlayerScore1 = itemView.findViewById(R.id.tvPlayerScore1);
        }
    }
}
