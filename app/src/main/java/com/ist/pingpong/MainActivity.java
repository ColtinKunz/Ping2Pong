package com.ist.pingpong;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ping2pong.R;

public class MainActivity extends AppCompatActivity implements AddPlayer.OnFragmentInteractionListener {
    private  int countScore = 0;
    private int countTime = 0;
    private Button startMinus;
    private Button startAdd;
    private  Button timeAdd;
    private Button timeMinus;
    private Button exits;
    private Button start;
    private TextView displayScores;
    private TextView displayTime;
    private MediaPlayer mediaPlayer;
    private String player1;
    private String player2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        buttonsPress();
        sound();

    }

    @Override
    protected void onStart() {
        super.onStart();
        ImageView rocketImage = findViewById(R.id.background);
        rocketImage.setImageResource(R.drawable.animate_background);
        AnimationDrawable rocketAnimation = (AnimationDrawable) rocketImage.getDrawable();
        rocketAnimation.start();
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
        intent.putExtra("p1", player1);
        intent.putExtra("p2", player2);
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        startActivity(intent);

    }

    public void exits(View view) {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
    public void sound(){
        mediaPlayer = MediaPlayer.create(this, R.raw.sound);
          if (!mediaPlayer.isPlaying()){
              mediaPlayer.start();
          }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                openFrag("s", "s");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openFrag(String s1, String s2) {
        start.setVisibility(View.GONE);
        AddPlayer addPlayer = AddPlayer.newInstance(s1, s2);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment, addPlayer, "addPlayer").commit();

    }

    @Override
    public void onFragmentInteraction(String s1, String s2) {
        player1 = s1;
        player2 = s2;
        onBackPressed();
        start.setVisibility(View.VISIBLE);
    }

}
