package com.ist.pingpong;

import android.graphics.RectF;

import java.util.Random;

public class Ball {
    private RectF rectangle;
    private float xSpeed;
    private float ySpeed;
    private float baseSpeed;
    private float BallWidth;
    private float BallHeight;
    public Ball(int screenX, int screenY){

        BallWidth = screenX / 100;
        BallHeight = BallWidth;

        ySpeed = screenY / 4;
        xSpeed = ySpeed;
        baseSpeed = xSpeed;

        rectangle = new RectF();

    }
    public RectF getRect(){
        return rectangle;
    }
    public void update(long fps){
        rectangle.left = rectangle.left + (xSpeed / fps);
        rectangle.top = rectangle.top + (ySpeed / fps);
        rectangle.right = rectangle.left + BallWidth;
        rectangle.bottom = rectangle.top - BallHeight;
    }
    public void reverseYVelocity(){
        ySpeed = -ySpeed;
    }
    public void reverseXVelocity(){
        xSpeed = -xSpeed;
    }
    public void setRandomXVelocity(){
        Random generator = new Random();
        int answer = generator.nextInt(2);
        if(answer == 0){
            reverseXVelocity();
        }
    }
    public void increaseVelocity(){
        xSpeed = xSpeed + xSpeed/10;
        ySpeed = ySpeed + ySpeed/10;
    }
    public void clearObstacleY(float y){
        rectangle.bottom = y;
        rectangle.top = y - BallHeight;
    }
    public void clearObstacleX(float x){
        rectangle.left = x;
        rectangle.right = x + BallWidth;
    }

    public void reset(int x, int y){
        rectangle.left = x / 2;
        rectangle.top = y - 20;
        rectangle.right = x / 2 + BallWidth;
        rectangle.bottom = y - 20 - BallHeight;

        //resets speed
        if (xSpeed != baseSpeed){
            xSpeed = baseSpeed;
            ySpeed = baseSpeed;
        }
        if (ySpeed > 0){reverseYVelocity();}

    }

}
