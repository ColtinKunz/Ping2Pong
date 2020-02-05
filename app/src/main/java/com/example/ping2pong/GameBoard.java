package com.example.ping2pong;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class GameBoard extends AppCompatActivity {
    boolean isRunning = false;
    long countDown ;
    TextView displayFinal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        Bundle extra = getIntent().getExtras();
        int countScore = extra.getInt("countScore", 10);
        int countTime = extra.getInt("countTime", 5);
        init();
        timeStart(countTime * 10000);



    }
    private void init(){
        displayFinal = findViewById(R.id.display);

    }
    private void timeStart(long count){
     new CountDownTimer( count,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countDown = millisUntilFinished;
                updateTime();
            }

            @Override
            public void onFinish() {
                displayFinal.setText("Game over");
            }
        }.start();
        isRunning = true;
    }
    private void updateTime() {
        long min = ((countDown / 1000) / 60);
        long sec = ((countDown / 1000) % 60);
        String fortTime = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
       displayFinal.setText(fortTime);
    }
}
