package com.example.ppolova.defender;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class EnemyShot extends Shot {

    private int dmg;

    public EnemyShot(int x, int y, int dmg, int speed) {
        super(x, y);

        this.dmg = dmg;

        this.direction = -1;

        this.height = 8;
        this.width = 12;

        this.speed = speed;

        this.rect = new Rect(x, y, x+width, y+height);
        this.color = Color.RED;
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

        if (Rect.intersects(this.rect, GamePanel.getInstance().getPlayer().getRectangle())) {
            this.toBeDeleted = true;
            GamePanel.getInstance().getPlayer().addHealth(-1 * this.dmg);
        }

        // strela vyletela z canvasu
        if(this.x < 0) {
            this.toBeDeleted = true;
        }
    }

}
