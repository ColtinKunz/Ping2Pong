package com.ist.pingpong;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ping2pong.R;

public class WinActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        Bundle extra = getIntent().getExtras();
        String msg = extra.getString("msg");
        TextView textView = findViewById(R.id.winMsg);
        textView.setText(msg);
        sound();
        stopSound();
    }

    public void sound() {
        mediaPlayer = MediaPlayer.create(this, R.raw.win_sound);
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Back is press", Toast.LENGTH_LONG).show();
            mediaPlayer.stop();
    }

    private void stopSound() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        if (count == 12) {
                            mediaPlayer.stop();
                            break;
                        }
                        count++;

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
