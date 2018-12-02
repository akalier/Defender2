package com.example.ppolova.defender;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.support.constraint.solver.widgets.Rectangle;

public class Shot implements GameObject {

    private int direction = 1;

    private int color;
    private Rect rect;
    private int width;
    private int height;
    private int speed;

    private int x;
    private int y;

    public boolean toBeDeleted = false;

    public Shot(int x, int y) {
        this.x = x;
        this.y = y;

        this.height = 8;
        this.width = 8;

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

        // strela vyletela z canvasu
        if(this.x > Constants.SCREEN_WIDTH + 5) {
            this.toBeDeleted = true;
        }
    }
}
