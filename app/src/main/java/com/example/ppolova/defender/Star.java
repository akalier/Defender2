package com.example.ppolova.defender;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

public class Star implements GameObject {

    private int color;
    private Rect rect;
    private int width;
    private int height;

    private int x;
    private int y;

    private int speed;


    public Star() {
        this.color = Color.rgb(96, 128, 145);

        int type = randomType();

        switch(type) {
            case 1:
                this.width = 2;
                this.height = 2;
                this.speed = 2;
                this.x = randomPositionX();
                this.y = randomPositionY();
                break;
            case 2:
                this.width = 3;
                this.height = 3;
                this.speed = 3;
                this.x = randomPositionX();
                this.y = randomPositionY();
                break;
            case 3:
                this.width = 4;
                this.height = 4;
                this.speed = 4;
                this.x = randomPositionX();
                this.y = randomPositionY();
                break;
            default:
                this.width = 2;
                this.height = 2;
                this.speed = 2;
                this.x = randomPositionX();
                this.y = randomPositionY();
                break;
        }

        this.rect = new Rect(x, y, x+width, y+height);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rect, paint);
    }

    @Override
    public void update() {
        x = x - speed;

        //hvezda vyletela z canvasu
        if(this.x <= 0) {
            this.x = Constants.SCREEN_WIDTH;
            this.y = randomPositionY();
        }

        rect.left = x;
        rect.right = x + width;

        rect.top = y;
        rect.bottom = y + height;

    }

    public int randomPositionX() {
        Random rand = new Random();
        int min = 0;
        int max = Constants.SCREEN_WIDTH;
        return rand.nextInt((max - min) + 1) + min;
    }

    public int randomPositionY() {
        Random rand = new Random();
        int min = 0;
        int max = Constants.SCREEN_HEIGHT;
        return rand.nextInt((max - min) + 1) + min;
    }

    public int randomType() {
        Random rand = new Random();
        int min = 1;
        int max = 3;
        return rand.nextInt((max - min) + 1) + min;
    }

}
