package com.example.ppolova.defender;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Enemy1 extends Enemy {

    private Bitmap enemyImg;

    public static final int HEIGHT = 14;
    public static final int WIDTH = 13;

    public Enemy1(Rect r) {
        super(r);

        this.enemyImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.enemy1);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        canvas.drawRect(super.getRectangle(), paint);
        canvas.drawBitmap(enemyImg, getRectangle().left, getRectangle().top, paint);
    }
}
