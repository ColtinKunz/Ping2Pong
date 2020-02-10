package com.ist.pingpong;

import android.graphics.RectF;

public class Bar {
    private RectF rectangle;
    private float barLength;
    private float barHeight;
    private float xCoord;
    private float yCoord;
    private float batSpeed;
    protected final int STOPPED = 0;
    protected final int LEFT = 1;
    protected final int RIGHT = 2;
    protected int batMoving = STOPPED;
    private int screenX;
    private int screenY;

    public Bar(int x, int y){
        screenX = x;
        screenY = y;
        barLength = screenX / 8;
        barHeight = screenY / 25;
        xCoord = screenX / 2;
        yCoord = screenY - 20;
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
        if(batMoving == RIGHT){ xCoord = xCoord + batSpeed / fps;
        }
        if (rectangle.left < 0) {
            xCoord = 0;
        }
        if (rectangle.right > screenX) {
            xCoord = screenX -(rectangle.right - rectangle.left);
        }
        rectangle.left = xCoord;
        rectangle.right = xCoord + barLength;
    }
}
