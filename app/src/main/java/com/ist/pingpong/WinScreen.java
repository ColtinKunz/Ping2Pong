package com.ist.pingpong;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ping2pong.R;

public class WinScreen extends AppCompatActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_screen);
        Bundle extra = getIntent().getExtras();
        String msg = extra.getString("msg" );
        textView = findViewById(R.id.winTextView);
        textView.setText(msg);
    }
}
