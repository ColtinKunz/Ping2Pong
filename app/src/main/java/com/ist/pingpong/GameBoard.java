package com.ist.pingpong;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class GameBoard extends Activity {

    MainThread mainThread;
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


    }
    @Override
    protected void onResume() {
        super.onResume();

        mainThread.resum();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mainThread.pause();
    }

}
