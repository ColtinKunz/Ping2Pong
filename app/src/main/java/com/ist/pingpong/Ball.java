package com.ist.pingpong;

import android.graphics.RectF;

import java.util.Random;

public class Ball {
    private RectF Rectangle;
    private float xSpeed;
    private float ySpeed;
    private float BallWidth;
    private float BallHeight;
    public Ball(int screenX, int screenY){

        BallWidth = screenX / 100;
        BallHeight = BallWidth;

        ySpeed = screenY / 4;
        xSpeed = ySpeed;

        Rectangle = new RectF();

    }
    public RectF getRect(){
        return Rectangle;
    }
    public void update(long fps){
        Rectangle.left = Rectangle.left + (xSpeed / fps);
        Rectangle.top = Rectangle.top + (ySpeed / fps);
        Rectangle.right = Rectangle.left + BallWidth;
        Rectangle.bottom = Rectangle.top - BallHeight;
    }
    public void reverseYVelocity(){
        ySpeed = -ySpeed;
    }
    public void reverseXVelocity(){
        xSpeed = -xSpeed;
    }
    public void setRandomXVelocity(){

        // Generate a random number either 0 or 1
        Random generator = new Random();
        int answer = generator.nextInt(2);

        if(answer == 0){
            reverseXVelocity();
        }
    }
    public void increaseVelocity(){
        xSpeed = xSpeed + xSpeed / 10;
        ySpeed = ySpeed + ySpeed / 10;
    }
    public void clearObstacleY(float y){
        Rectangle.bottom = y;
        Rectangle.top = y - BallHeight;
    }
    public void clearObstacleX(float x){
        Rectangle.left = x;
        Rectangle.right = x + BallWidth;
    }

    public void reset(int x, int y){
        Rectangle.left = x / 2;
        Rectangle.top = y - 20;
        Rectangle.right = x / 2 + BallWidth;
        Rectangle.bottom = y - 20 - BallHeight;
    }

}
