package com.example.ppolova.defender;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Player implements GameObject {

    public static final int WIDTH = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player).getWidth();
    public static final int HEIGHT = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player).getHeight();
    private Rect rectangle;
    private Rect touchRectangle;
    public static final int TOUCH_AREA_Y = 50;
    private int color;

    private int dmg;
    private int score;
    private int health;

    private Bitmap playerImg;

    public Player(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.touchRectangle = new Rect(0, rectangle.top - TOUCH_AREA_Y, Constants.SCREEN_WIDTH / 2 - 50, rectangle.bottom + TOUCH_AREA_Y);
        this.color = color;
        playerImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player);
        this.dmg = 10;
        this.score = 0;
    }

    public Rect getRectangle() {
        return rectangle;
    }

    public Rect getTouchRectangle() {
        return touchRectangle;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
        canvas.drawBitmap(playerImg, rectangle.left, rectangle.top, paint);
    }

    @Override
    public void update() {

    }

    public void update(Point point) {
        rectangle.set(point.x - WIDTH / 2, point.y - HEIGHT / 2, point.x + WIDTH / 2, point.y + HEIGHT / 2);
        touchRectangle.set(0, rectangle.top - TOUCH_AREA_Y, Constants.SCREEN_WIDTH / 2 - 50, rectangle.bottom + TOUCH_AREA_Y);
    }

    public void reset() {
        this.score = 0;
    }

    public int getDmg() {
        return dmg;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += score;
    }
}
