package com.example.freakingmath_clone;

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
        viewHolder.tvNumber.setText("" + 1);
        viewHolder.tvPlayerName1.setText(playerModel.getPlayerName());
        viewHolder.tvPlayerScore1.setText(playerModel.getPlayerScore() + "");

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
