package com.ist.pingpong;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class MainThread extends SurfaceView implements Runnable {
    private Thread mGameThread = null;
    private SurfaceHolder holder;
    private volatile boolean isPlaying;
    private boolean isPaused = true;
    private Canvas canvas;
    private Paint paint;
    private long trackChangeDirectionOfBall;
    private int screenX;
    private int screenY;
    private Bar bar1;
    private Bar2 bar2;
    private Ball ball;
    private SoundPool soundPool;
    private int beep1 = -1;
    private int beep2 = -1;
    private int beep3 = -1;
    private int looseLife = -1;
    private int tractScorePlayer1 = 0;
    private int tractScorePlayer2 = 0;
    private int tractTime;
    private int generalScore;
    private int winning = 0;
    private String player1;
    private String player2;

    public MainThread(Context context, int x, int y) {
        super(context);
        screenX = x;
        screenY = y;
        holder = getHolder();
        paint = new Paint();
        bar1 = new Bar(screenX, screenY);
        bar2 = new Bar2(screenX, screenY);
        ball = new Ball(screenX, screenY);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else {
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        }


        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("beep_one.mp3");
            beep1 = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("beep_two.mp3");
            beep2 = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("beep_three.mp3");
            beep3 = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("lose_life.mp3");
            looseLife = soundPool.load(descriptor, 0);

        } catch (IOException e) {
            e.printStackTrace();
        }
        setupAndRestart();
    }

    public void setupAndRestart() {
        ball.reset(screenX, screenY);
        if (tractTime == 0 || tractScorePlayer1 == getGeneralScore() || tractScorePlayer2 == getGeneralScore()) {
            tractScorePlayer1 = 0;
            tractScorePlayer2 = 0;
            tractTime = 0;
        }
    }

    @Override
    public void run() {
        while (isPlaying) {
            long startFrameTime = System.currentTimeMillis();
            if (!isPaused) {
                update();
            }
            draw();
            long timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                trackChangeDirectionOfBall = 1000 / timeThisFrame;
            }

        }

    }

    public void update() {
        bar1.update(trackChangeDirectionOfBall);
        bar2.update(trackChangeDirectionOfBall);
        ball.update(trackChangeDirectionOfBall);
        if (RectF.intersects(bar1.getRect(), ball.getRect())) {
            ball.setRandomXVelocity();
            ball.reverseYVelocity();
            ball.clearObstacleY(bar1.getRect().top - 2);
            tractScorePlayer1++;
            ball.increaseVelocity();
            soundPool.play(beep1, 1, 1, 0, 0, 1);
        }
        //second bar, the bar at the top
        if (RectF.intersects(bar2.getRect(), ball.getRect())) {
            ball.setRandomXVelocity();
            ball.reverseYVelocity();
            ball.clearObstacleY(bar2.getRect().bottom - 2);
            ball.increaseVelocity();
            soundPool.play(beep1, 1, 1, 0, 0, 1);
        }
        if (ball.getRect().bottom > screenY) {
            ball.reverseYVelocity();
            ball.clearObstacleY(screenY - 2);
            tractTime--;
            tractScorePlayer2++;
            soundPool.play(looseLife, 1, 1, 0, 0, 1);
            if (tractTime < 1 || tractScorePlayer1 == getGeneralScore() || tractScorePlayer2 == getGeneralScore()) {
                isPaused = true;
                setupAndRestart();
            }
        }
        if (ball.getRect().top < 0) {
            ball.reverseYVelocity();
            ball.clearObstacleY(12);
            soundPool.play(beep2, 1, 20, 0, 0, 1);
        }
        if (ball.getRect().left < 0) {
            ball.reverseXVelocity();
            ball.clearObstacleX(2);
            soundPool.play(beep3, 1, 1, 0, 0, 1);
        }
        if (ball.getRect().right > screenX) {
            ball.reverseXVelocity();
            ball.clearObstacleX(screenX - 22);
            soundPool.play(beep3, 1, 1, 0, 0, 1);

        }

    }
    public void draw() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.argb(255, 0, 0, 0));
            paint.setColor(Color.argb(255, 255, 255, 0));
            canvas.drawRect(bar1.getRect(), paint);
            canvas.drawRect(bar2.getRect(), paint);
            canvas.drawRect(ball.getRect(), paint);
            paint.setColor(Color.argb(255, 255, 255, 255));
            paint.setTextSize(40);
            canvas.drawText(player1 + " Score: " + tractScorePlayer1 + "   Time: " + tractTime, 10, screenY - 20, paint);
            canvas.drawText(player2 + " Score: " + tractScorePlayer2 + "   Time: " + tractTime, 10, 50, paint);
            canvas.drawText("---------------------------------------------------------------------------------------------------- ",
                    0, screenY / 2, paint);
            holder.unlockCanvasAndPost(canvas);

        }

    }

    public void celebrate(Paint paint, float desiredWidth, String text) {
        final float testTextSize = 48f;
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        float desiredTextSize = testTextSize * desiredWidth / bounds.width();
        paint.setTextSize(desiredTextSize);
    }

    public void pause() {
        isPlaying = false;
        try {
            mGameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    public void resum() {
        isPlaying = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                isPaused = false;
                if (motionEvent.getX() > screenX / 2) {
                    if (motionEvent.getRawY() >= 0 && motionEvent.getRawY() < 500) {
                        bar2.setMovementState(bar2.RIGHT);
                    } else {
                        bar1.setMovementState(bar1.RIGHT);
                    }

                } else {
                    if (motionEvent.getRawY() > 500) {
                        bar1.setMovementState(bar1.LEFT);

                    } else {
                        bar2.setMovementState(bar2.LEFT);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                bar1.setMovementState(bar1.STOPPED);
                bar2.setMovementState(bar2.STOPPED);
        }
        return true;
    }

    public void setTractScore(int tractScorePlayer1) {
        generalScore = tractScorePlayer1;
    }

    public void setTractTime(int tractTime) {
        this.tractTime = tractTime;
    }

    public int getGeneralScore() {
        return generalScore;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }
}
