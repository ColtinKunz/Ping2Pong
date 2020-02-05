package com.example.ping2pong;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class GameBoard extends AppCompatActivity {
    boolean isRunning = false;
    long countDown ;
    float moveBar = 0;
    TextView displayFinal;
    ImageView player1Bar;
    ImageView player2Bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        Bundle extra = getIntent().getExtras();
        int countScore = extra.getInt("countScore", 10);
        int countTime = extra.getInt("countTime", 5);
        init();
        timeStart(countTime * 10000);
        setUp();


    }
    private void init(){
        displayFinal = findViewById(R.id.display);
        player1Bar = findViewById(R.id.player1Bar);
        player2Bar = findViewById(R.id.player2Bar);

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
        String
                fortTime = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
       displayFinal.setText(fortTime);
    }
    private void handleMovementOfBar(float moveTo, View view){
        Path path = new Path();
        path.lineTo(0.25f, 0.25f);
        path.moveTo(moveTo, 0f);
        path.lineTo(1f, 1f);
        ObjectAnimator animation = ObjectAnimator.ofFloat(view,"x" ,"y", path);
        //animation.setInterpolator(new PathInterpolator(0.9f, 0.1f, 0.5f, 0.9f));
        animation.start();
    }
    private void handletTouch(View view, MotionEvent motionEvent){
      int move = motionEvent.getActionMasked();
      switch (move){
          case MotionEvent.ACTION_UP:
            //  moveBar += 10;
              handleMovementOfBar(.2f, view);
              break;
          case  MotionEvent.ACTION_DOWN:
              //moveBar -= 10;
              handleMovementOfBar(-.2f, view);
              break;
      }
    }
    private void setUp(){
        player1Bar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handletTouch(v, event);
                return true;
            }
        });
    }
}
