package com.example.freakingmath_clone;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PlayActivity extends AppCompatActivity {

    // time to play each level
    private final int TIME_PLAY_EACH_LEVEL = 2000;

    // define background color of play screen
    private String[] arrColor = {"#FA8072", "#DC143C", "#B22222", "#FF4500", "#FF8C00", "#32CD32", "#328B22", "#008000", "#9ACD32", "#8FBC8F", "#556B2F", "#20B2AA"};

    //init
    private TextView tvOperator, tvResult, tvHighScore, tvScore;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;

    // timer
    private CountDownTimer countDownTimer;
    private Timer timer;
    private TimerTask timerTask;
    // player score
    private int score = 0;
    private int highScore = 0;
    // level model
    private LevelModel levelModel;
    private Random rand;

    //oop
    private PlayerModel playerModel;
    private ArrayList<PlayerModel> playerModels = new ArrayList<>();

    //dialog
    private TextView tvPlayerScore;
    private EditText edtPlayerName;
    private Button btnConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play);

        init();
    }

    private void setCountDownTimer() {
        progressBar.setMax(2);
        progressBar.setProgress(0);
        countDownTimer = new CountDownTimer(TIME_PLAY_EACH_LEVEL, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                int current = progressBar.getProgress();
                if (current > progressBar.getMax()) {
                    current = 0;
                }
                current += progressBar.getMax() * 10 / TIME_PLAY_EACH_LEVEL;
                progressBar.setProgress(current);
                Log.i("TestTimer", current + "");
            }

            @Override
            public void onFinish() {
                progressBar.setProgress(0);
            }
        };
        countDownTimer.start();
    }

    private void init() {
        tvHighScore = findViewById(R.id.tvHighScore);
        tvScore = findViewById(R.id.tvScore);
        tvOperator = findViewById(R.id.tvOperator);
        tvResult = findViewById(R.id.tvResult);
        progressBar = findViewById(R.id.progressBar);
        linearLayout = findViewById(R.id.linear);

        playerModel = new PlayerModel();

        tvHighScore.setText("High Score: " + getHighScore());
        tvScore.setText("Score: " + score);

        // create random
        rand = new Random();

        // generate the first level
        levelModel = GenerateLevel.generateLevel(1);
        // show level info on screen
        displayNewLevel(levelModel);

        // create timer, timertask
        createTimerTask();

        countHighScore();
    }

    private void displayNewLevel(LevelModel levelModel) {
        tvOperator.setText(levelModel.strOperator);
        tvResult.setText(levelModel.strResult);

        int indexColor = rand.nextInt(arrColor.length);
        linearLayout.setBackgroundColor(Color.parseColor(arrColor[indexColor]));
        setCountDownTimer();
    }

    private void createTimerTask() {
        // create timer
        timer = new Timer();

        // create time task
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // show game over screen
                        showGameOver(score);
                    }
                });
            }
        };
        // set time to run timertask
        timer.schedule(timerTask, TIME_PLAY_EACH_LEVEL);
    }

    private void showGameOver(final int score) {
        // cancel timer
        cancelTimer();

        // show gameover UI
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage("Your score: " + score)
                .setPositiveButton("Replay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        // Play again
                        tvHighScore.setText("High Score: " + getHighScore());
                        tvScore.setText("0");
                        PlayActivity.this.score = 0;
                        PlayActivity.this.nextLevel(score);
                    }
                })
                .setNegativeButton("Home", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        // Back to home activity
                        Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

        //Save new high score
        saveNewHighScore(score);
    }

    private void saveNewHighScore(int score) {
        loadHighScore();
        SharedPreferences myHighScore = getSharedPreferences("MyHighScore", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myHighScore.edit();
        Gson gson = new Gson();
        if (countHighScore() < 5) {
            playerModels.add(new PlayerModel("BQH", score));
        } else {
            if (score > playerModels.get(4).getPlayerScore()) {
                playerModels.remove(4);
                playerModels.add(4, new PlayerModel("BQH new", score));
                for(int i = 0; i <= playerModels.size() -1;i++){
                    Log.i("111", playerModels.get(i).getPlayerName() + " " + playerModels.get(i).getPlayerScore());
                }
            }
        }
        String json = gson.toJson(playerModels);
        editor.putString("score", json);
        editor.commit();
    }

    private int countHighScore() {
        loadHighScore();
        if (playerModels.size() > 1) {
            return playerModels.size();
        } else {
            return 0;
        }
    }

    private void loadHighScore() {
        SharedPreferences myHighScore = getSharedPreferences("MyHighScore", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = myHighScore.getString("score", null);
        Type type = new TypeToken<ArrayList<PlayerModel>>() {
        }.getType();
        playerModels = gson.fromJson(json, type);
        sortByScore(playerModels);

        if (playerModels == null) {
            playerModels = new ArrayList<>();
        }
    }

    private int getHighScore() {
        loadHighScore();
        if (playerModels.size() > 0) {
            return playerModels.get(0).getPlayerScore();
        } else {
            return 0;
        }

    }

    public void btnWrong(View view) {
        if (levelModel.correctORwrong == false) {
            score += 1;
            tvScore.setText("Score: " + score);

            //generate next level
            nextLevel(score);
        } else {
            showGameOver(score);
        }
    }

    public void btnTrue(View view) {
        if (levelModel.correctORwrong == true) {
            score += 1;
            tvScore.setText("Score: " + score);

            //generate next level
            nextLevel(score);
        } else {
            showGameOver(score);
        }
    }

    private void nextLevel(int score) {
        // cancel timer
        cancelTimer();

        // set new time for next level
        createTimerTask();

        // update ui
        levelModel = GenerateLevel.generateLevel(score);
        displayNewLevel(levelModel);
    }

    private void cancelTimer() {
        timerTask.cancel();
        timer.cancel();
    }

    //show dialog high score
    private void showDialogThemLop(int score) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_high_score_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        tvPlayerScore = dialog.findViewById(R.id.tvPlayerScore);
        edtPlayerName = dialog.findViewById(R.id.edtPlayerName);
        btnConfirm = dialog.findViewById(R.id.btnConfirm);

        tvPlayerScore.setText("Player Score: " + score);
        String playerName = edtPlayerName.getText().toString();

        playerModel.setPlayerName(playerName);
        playerModel.setPlayerScore(score);
        playerModels.add(playerModel);
    }

    private void sortByScore(ArrayList<PlayerModel> playerModels) {
        if (playerModels != null && playerModels.size() > 1) {
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
}
