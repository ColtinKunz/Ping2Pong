package com.ist.pingpong;

import android.graphics.RectF;

public class Bar2 {
    private RectF rectangle;
    private float barLength;
    private float barHeight;
    private float xCoord;
    private float yCoord;
    private float batSpeed;
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;
    private int batMoving = STOPPED;
    private int screenX;
    private int screenY;

    public Bar2(int x, int y){

        screenX = x;
        screenY = y;

        barLength = screenX / 8;
        barHeight = screenY / 75;
        xCoord = screenX / 2;
        yCoord = 0;

       rectangle = new RectF(xCoord, yCoord, xCoord + barLength, yCoord + barHeight);
        batSpeed = screenX;
    }
    public RectF getRect(){
        return rectangle;
    }
    public void setMovementState(int state){
        batMoving = state;
    }
    public void update(long fps){

        if(batMoving == LEFT){
            xCoord = xCoord - batSpeed / fps;
        }

        if(batMoving == RIGHT){
            xCoord = xCoord + batSpeed / fps;
        }

        if(rectangle.left < 0){ xCoord = 0; } if(rectangle.right > screenX){
            xCoord = screenX - (rectangle.right - rectangle.left);
        }
        rectangle.left = xCoord;
        rectangle.right = xCoord + barLength;
    }
}
