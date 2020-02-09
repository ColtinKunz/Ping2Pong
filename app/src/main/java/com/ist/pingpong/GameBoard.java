package com.ist.pingpong;

import android.app.Activity;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ping2pong.R;
import com.google.android.material.snackbar.Snackbar;

public class GameBoard extends Activity {
private  MediaPlayer mediaPlayer;
  private   MainThread mainThread;
  private   int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Bundle extra = getIntent().getExtras();
        int countScore = extra.getInt("countScore", 10);
        int countTime = extra.getInt("countTime", 5);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        mainThread = new MainThread(this, size.x, size.y);
        mainThread.setTractScore(countScore);
        mainThread.setTractTime(countTime);
        setContentView(mainThread);
        alert(countTime);
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
        mediaPlayer = MediaPlayer.create(this, R.raw.rock);
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

}
