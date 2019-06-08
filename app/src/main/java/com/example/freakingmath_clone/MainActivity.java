package com.example.freakingmath_clone;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private TextView tvNumber;
    private TextView tvPlayerName1;
    private TextView tvPlayerScore1;
    private RecyclerView recycler;
    private Button btnCancel;

    //oop
    private PlayerModel playerModel;
    private ArrayList<PlayerModel> playerModels;
    private LeaderBoard_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove titlebar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        playerModels = new ArrayList<>();
        SharedPreferences myHighScore = getSharedPreferences("MyHighScore", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myHighScore.edit();
        Gson gson = new Gson();
        playerModels.add(new PlayerModel("Quang Hưng", 100));
        playerModels.add(new PlayerModel("Quang Hưng1", 200));
        String json = gson.toJson(playerModels);
        editor.putString("score", json);
        editor.commit();

        playerModels.clear();
        loadHighScore();
    }

    public void btnPlay(View view) {
        Intent intent = new Intent(MainActivity.this, PlayActivity.class);
        startActivity(intent);
        finish();
    }

    public void btnLeaderBoard(View view) {
        showDialogThemLop();
    }

    private void loadHighScore() {
        SharedPreferences myHighScore = getSharedPreferences("MyHighScore", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = myHighScore.getString("score", null);
        Type type = new TypeToken<ArrayList<PlayerModel>>() {
        }.getType();
        playerModels = gson.fromJson(json, type);
        sortByScore();
        int listSize = playerModels.size();
        for (int i = 0; i < listSize; i++) {
            Log.i("111", playerModels.get(i).getPlayerName() + " " + playerModels.get(i).getPlayerScore() + "\n listSize:" + listSize);
        }

        if (playerModels == null) {
            playerModels = new ArrayList<>();
        }
    }

    private void sortByScore() {
        if (playerModels != null) {
            Collections.sort(playerModels, new Comparator<PlayerModel>() {
                @Override
                public int compare(PlayerModel o1, PlayerModel o2) {
                    double score1 = o1.getPlayerScore();
                    double score2 = o2.getPlayerScore();

                    if (score1 < score2) {
                        return 1;
                    } else {
                        if (score1 == score2) {
                            return 0;
                        } else {
                            return -1;
                        }
                    }
                }
            });
        }
    }

    private void showDialogThemLop() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_leader_board_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);

        tvNumber = dialog.findViewById(R.id.tvNumber);
        tvPlayerName1 = dialog.findViewById(R.id.tvPlayerName1);
        tvPlayerScore1 = dialog.findViewById(R.id.tvPlayerScore1);
        recycler = dialog.findViewById(R.id.recycler);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        setAdapter();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void setAdapter() {
        if (adapter == null) {
            adapter = new LeaderBoard_Adapter(playerModels);
            recycler.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
