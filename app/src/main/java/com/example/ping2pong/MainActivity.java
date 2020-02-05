package com.example.ping2pong;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    int countScore = 0;
    int countTime = 0;
    Button startMinus;
    Button startAdd;
    Button timeAdd;
    Button timeMinus;
    Button exits;
    Button start;
    TextView displayScores;
    TextView displayTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        buttonsPress();
    }
    private void init(){
        start = findViewById(R.id.start);
        startMinus = findViewById(R.id.startMinus);
        startAdd = findViewById(R.id.startAdd);
        timeAdd = findViewById(R.id.timeAdd);
        timeMinus = findViewById(R.id.timeMinus);
        exits = findViewById(R.id.exits);
        displayScores = findViewById(R.id.displayScores);
        displayTime = findViewById(R.id.displayTime);
    }
    private void buttonsPress(){
        startMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countScore > 1){
                    countScore--;
                }
                displayScores.setText(countScore + "");
            }
        });
        startAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countScore < 10){
                    countScore++;
                }
                displayScores.setText(countScore +"");
            }
        });
        timeMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countTime > 1){
                    countTime--;
                }
                displayTime.setText(countTime + "");
            }
        });
        timeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countTime < 10){
                    countTime++;
                }
                displayTime.setText(countTime +"");
            }
        });
    }

    public void start(View view) {
        Intent intent = new Intent(this, GameBoard.class);
        intent.putExtra("countScore", countScore);
        intent.putExtra("countTime", countTime);
        startActivity(intent);
    }

    public void exits(View view) {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
