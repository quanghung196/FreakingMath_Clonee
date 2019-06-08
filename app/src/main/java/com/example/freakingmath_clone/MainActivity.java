package com.example.freakingmath_clone;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
    private ImageView imgLeaderBoard;

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

        init();

    }

    private void init(){
        imgLeaderBoard = findViewById(R.id.imgLeaderBoard);
    }

    public void btnPlay(View view) {
        Intent intent = new Intent(MainActivity.this, PlayActivity.class);
        startActivity(intent);
        finish();
    }

    public void imgLeaderBoard(View view) {
        showPopupWindow(imgLeaderBoard);
    }

    private void loadHighScore() {
        SharedPreferences myHighScore = getSharedPreferences("MyHighScore", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = myHighScore.getString("score", null);
        Type type = new TypeToken<ArrayList<PlayerModel>>() {
        }.getType();
        playerModels = gson.fromJson(json, type);
        sortByScore();

        if (playerModels == null) {
            playerModels = new ArrayList<>();
        }
    }

    private void sortByScore() {
        if (playerModels != null && playerModels.size()>1) {
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

    private void showPopupWindow(View view) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_leader_board, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        tvNumber = popupView.findViewById(R.id.tvNumber);
        tvPlayerScore1 = popupView.findViewById(R.id.tvPlayerScore1);
        tvPlayerName1 = popupView.findViewById(R.id.tvPlayerName1);
        recycler = popupView.findViewById(R.id.recycler);
        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        recycler.setLayoutManager(llm);
        loadHighScore();
        setAdapter();
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
