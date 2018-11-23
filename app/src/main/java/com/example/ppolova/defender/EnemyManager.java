package com.example.ppolova.defender;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

public class EnemyManager {

    private ArrayList<Enemy> enemies;
    private int playerGap;
    private int enemyGap;
    private int enemyHeight;
    private int color;

    private long startTime;
    private long initTime;

    private int score = 0;

    public EnemyManager() {

        startTime = initTime = System.currentTimeMillis();

        //create enemies
        enemies = new ArrayList<>();
        populateEnemies();
    }

    public boolean playerCollision(Player player) {
        for (Enemy enemy : enemies) {
            if (enemy.playerCollision(player)) return true;
        }
        return false;
    }

    private void populateEnemies() {
        //spawn enemies outside the screen
        //int currY = -5*Constants.SCREEN_HEIGHT/4;
        int currX = 2*Constants.SCREEN_WIDTH;

        while(currX > Constants.SCREEN_WIDTH) {
            //random x position in the screen
            int yStart = (int)(Math.random()*(Constants.SCREEN_HEIGHT));
            enemies.add(new Enemy1(new Rect(currX, yStart, currX+Enemy1.WIDTH, yStart+Enemy1.HEIGHT)));
            currX -= 100;

        }
    }

    public void update() {

        //time passed since the beginning
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);

        //start time
        startTime = System.currentTimeMillis();

        //speed of enemies is increasing over time
        float speed = (float)(Math.sqrt(1 +(startTime - initTime)/4000.0)) * Constants.SCREEN_HEIGHT/(10000.0f);

        //move enemies
        for (Enemy enemy : enemies) {
            enemy.move(speed * elapsedTime);
        }

        //if first enemy ran outside the screen, remove it and add a new one
        if (enemies.get(enemies.size() - 1).getRectangle().right < 0) {
            int yStart = (int)(Math.random()*(Constants.SCREEN_HEIGHT));
            int currX = 5*Constants.SCREEN_WIDTH/4;
            enemies.add(0, (new Enemy1(new Rect(currX, yStart, currX+Enemy1.WIDTH, yStart+Enemy1.HEIGHT))));
            enemies.remove(enemies.size() - 1);
            //increment score points
            score++;
        }
    }

    public void draw(Canvas canvas) {
        //draw enemies
        for (Enemy enemy : enemies) {
            enemy.draw(canvas);
        }
        //draw score
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.MAGENTA);
        canvas.drawText("" + score, 50, 50 + paint.descent() - paint.ascent(), paint);
    }

}
