package com.example.ppolova.defender;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class Enemy implements GameObject {

    private Rect rectangle;

    private Bitmap enemyImg;

    protected int health;
    protected int points;

    public boolean dead = false;

    public Rect getRectangle() {
        return rectangle;
    }

    public void move(float x) {
        rectangle.left -= x;
        rectangle.right -= x;
    }

    public Enemy(Rect r) {

        this.rectangle = r;
    }

    public boolean playerCollision(Player player) {

        return Rect.intersects(rectangle, player.getRectangle());
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void update() {

    }

}
