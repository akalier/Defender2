package com.example.ppolova.defender;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class EnemyShot extends Shot {

    private int dmg;
    private Preferencies preferencies;

    private int difficulty;

    public EnemyShot(int x, int y, int dmg, int speed) {
        super(x, y);

        this.dmg = dmg;

        this.direction = -1;

        this.height = 8;
        this.width = 12;

        this.speed = speed;

        this.rect = new Rect(x, y, x+width, y+height);
        this.color = Color.RED;

        this.preferencies = new Preferencies(Constants.CURRENT_CONTEXT);
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

        // shot hits player
        if (Rect.intersects(this.rect, GamePanel.getInstance().getPlayer().getRectangle())) {
            this.toBeDeleted = true;

            // descrease health
            GamePanel.getInstance().getPlayer().addHealth(-1 * this.dmg * (difficulty+1));
        }

        // shot ran out of canvas
        if(this.x < 0) {
            this.toBeDeleted = true;
        }
    }

}
