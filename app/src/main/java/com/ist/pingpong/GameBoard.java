package com.ist.pingpong;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.ping2pong.R;
import com.google.android.material.snackbar.Snackbar;

public class GameBoard extends Activity {
private  MediaPlayer mediaPlayer;
  private   MainThread mainThread;
  private   int count = 0;
    private int countTime;
    private String checking;
    private CountDownTimer countDownTimer;
    private boolean isRunning;
    private long timeLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Bundle extra = getIntent().getExtras();
        int countScore = extra.getInt("countScore", 10);
        countTime = extra.getInt("countTime", 5);
        String player1 = extra.getString("p1");
        String player2 = extra.getString("p2");
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mainThread = new MainThread(this, size.x, size.y);
        mainThread.setPlayer1(player1);
        mainThread.setPlayer2(player2);
        setContentView(mainThread);
        alert(countTime);
        mainThread.startTime(countTime * 60000);
        checkGameStatus(countTime * 60000);

    }
    @Override
    protected void onResume() {

        super.onResume();
        mainThread.resum();
        try{
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        mainThread.pause();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sound();
        mediaPlayer.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try{
                        Thread.sleep(1000);
                        count++;
                        if (count == 6){
                            mediaPlayer.stop();
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();

    }
    private void sound(){
        mediaPlayer = MediaPlayer.create(this, R.raw.sound);
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();

        }
    }
    private void alert(int countTime) {
        if (countTime == 0) {
            View contextView = findViewById(android.R.id.content);
            Snackbar.make(contextView, "default time is 1 minute", Snackbar.LENGTH_SHORT)
                    .show();

        }
    }

    private void checkGameStatus(long tractTime) {
        countDownTimer = new CountDownTimer(tractTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(GameBoard.this, WinActivity.class);
                intent.putExtra("msg", mainThread.msg());
                startActivity(intent);
            }
        }.start();
        isRunning = true;

    }

}
