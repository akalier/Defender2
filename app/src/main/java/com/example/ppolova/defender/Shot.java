package com.example.ppolova.defender;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.support.constraint.solver.widgets.Rectangle;

public class Shot implements GameObject {

    protected int direction = 1;

    protected int color;
    protected Rect rect;
    protected int width;
    protected int height;
    protected int speed;

    protected int x;
    protected int y;

    public boolean toBeDeleted = false;

    public Shot(int x, int y) {
        this.x = x;
        this.y = y;

        this.height = 8;
        this.width = 12;

        this.speed = 5;

        this.rect = new Rect(x, y, x+width, y+height);
        this.color = Color.BLUE;
    }


    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rect, paint);
    }

    @Override
    public void update() {
        x = x + (speed * direction);
        rect.left = x;
        rect.right = x + width;

        rect.top = y;
        rect.bottom = y + height;

        // strela trefila enemyho
        for (Enemy enemy : GamePanel.getInstance().getEnemyManager().getEnemies()) {
            if (Rect.intersects(this.rect, enemy.getRectangle())) {
                this.toBeDeleted = true;
                enemy.health -= GamePanel.getInstance().getPlayer().getDmg();
                // strela zabila enemyho
                if (enemy.health <= 0) {
                    GamePanel.getInstance().getPlayer().setScore(enemy.points);
                    enemy.dead = true;
                }
            }
        }

        // strela zasahla enemy shot
        for (EnemyShot enemyShot : GamePanel.getInstance().getEnemyShots()) {
            if (Rect.intersects(this.rect, enemyShot.getRect())) {
                this.toBeDeleted = true;
                enemyShot.toBeDeleted = true;
            }
        }

        // strela vyletela z canvasu
        if(this.x > Constants.SCREEN_WIDTH + 5) {
            this.toBeDeleted = true;
        }
    }

    public Rect getRect() {
        return rect;
    }
}
