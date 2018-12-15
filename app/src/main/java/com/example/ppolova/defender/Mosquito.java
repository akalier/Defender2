package com.example.ppolova.defender;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Mosquito extends Enemy {

    private Bitmap enemyImg;

    public static final int HEIGHT = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.enemy1).getHeight();
    public static final int WIDTH = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.enemy1).getWidth();

    public Mosquito(Rect r) {
        super(r);

        this.enemyImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.enemy1);
        this.health = 10;
        this.points = 10;
        this.speed = 8;
        this.dmg = 2;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        //paint.setColor(Color.RED);
        canvas.drawRect(super.getRectangle(), paint);
        canvas.drawBitmap(enemyImg, getRectangle().left, getRectangle().top, paint);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
