package com.example.ppolova.defender;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Crab extends Enemy {

    private Bitmap enemyImg;

    public static final int HEIGHT = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.enemy2).getHeight();
    public static final int WIDTH = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.enemy2).getWidth();

    public Crab(Rect r) {
        super(r);

        this.enemyImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.enemy2);
        this.health = 30;
        this.points = 20;
        this.speed = 4;
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
