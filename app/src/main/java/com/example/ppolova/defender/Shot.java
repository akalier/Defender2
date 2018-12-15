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

    private Preferencies preferencies;
    private int difficulty;

    public boolean toBeDeleted = false;

    public Shot(int x, int y) {
        this.x = x;
        this.y = y;

        this.height = 8;
        this.width = 12;

        this.speed = 5;

        this.rect = new Rect(x, y, x+width, y+height);
        this.color = Color.BLUE;

        preferencies = new Preferencies(Constants.CURRENT_CONTEXT);
        this.difficulty = preferencies.getDifficulty();
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

        // shot hit enemy
        for (Enemy enemy : GamePanel.getInstance().getEnemyManager().getEnemies()) {
            if (Rect.intersects(this.rect, enemy.getRectangle())) {
                this.toBeDeleted = true;
                // decrease enemy's health
                enemy.health -= GamePanel.getInstance().getPlayer().getDmg();
                // shot killed enemy
                if (enemy.health <= 0) {
                    // increase score - the higher difficulty, the more points
                    GamePanel.getInstance().getPlayer().setScore(enemy.points*(difficulty+1));
                    enemy.dead = true;
                }
            }
        }

        // shot hit enemy shot
        for (EnemyShot enemyShot : GamePanel.getInstance().getEnemyShots()) {
            if (Rect.intersects(this.rect, enemyShot.getRect())) {
                this.toBeDeleted = true;
                enemyShot.toBeDeleted = true;
            }
        }

        // shot ran out of canvas
        if(this.x > Constants.SCREEN_WIDTH + 5) {
            this.toBeDeleted = true;
        }
    }

    public Rect getRect() {
        return rect;
    }
}
