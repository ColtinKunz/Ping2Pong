package com.ist.pingpong;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ping2pong.R;

public class WinActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        Bundle extra = getIntent().getExtras();
        String msg = extra.getString("msg");
        TextView textView = findViewById(R.id.winMsg);
        textView.setText(msg);
        sound();
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
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }
}
