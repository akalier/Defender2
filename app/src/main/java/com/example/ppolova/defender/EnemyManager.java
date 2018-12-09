package com.example.ppolova.defender;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class EnemyManager {

    private ArrayList<Enemy> enemies;

    private long startTime;
    private long initTime;
    private long spawnTime;
    private long fastenerTime;

    private int fastener = 0;

    private int currX = 4*(Constants.SCREEN_WIDTH/4);

    Random r = new Random();

    public EnemyManager() {

        startTime = initTime = spawnTime = fastenerTime = System.currentTimeMillis();

        //create enemies
        enemies = new ArrayList<>();
        //populateEnemies();
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

        while(currX > Constants.SCREEN_WIDTH) {
            //random y position in the screen
            int yStart = (int)(Math.random()*(Constants.SCREEN_HEIGHT));
            enemies.add(new Mosquito(new Rect(currX, yStart, currX+Mosquito.WIDTH, yStart+Mosquito.HEIGHT)));
            currX -= 300;
        }

        currX = 5*(Constants.SCREEN_WIDTH/4);
    }

    public void update() {

        //time passed since the beginning
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);

        //start time
        startTime = System.currentTimeMillis();

        //speed of enemies is increasing over time
        //float speed = (float)(Math.sqrt(1 +(startTime - initTime)/8000.0)) * Constants.SCREEN_HEIGHT/(10000.0f);

        //move enemies
        for (Enemy enemy : enemies) {
            enemy.move(enemy.speed+this.fastener);
            if (enemy.getRectangle().right < 0) {
                enemy.dead = true;

                GamePanel.getInstance().getPlayer().setScore(-(enemy.points));
            }
        }

        List<Enemy> newEnemies = new ArrayList<Enemy>();

        ListIterator<Enemy> iter = enemies.listIterator();
        while(iter.hasNext()){
            if(iter.next().dead){
                iter.remove();
            }
        }

        double spawnThreshold =  (2000.0-elapsedTime)>100 ? (2000.0-elapsedTime) : 100;

        if ((startTime - spawnTime) > spawnThreshold) {
            startTime = spawnTime = System.currentTimeMillis();
            int enemyType = r.nextInt(4 - 1) + 1;
            newEnemies.add(spawnEnemy(enemyType));
        }

        if ((startTime - fastenerTime) > 15000) {
            fastenerTime = System.currentTimeMillis();
            this.fastener++;
        }

        enemies.addAll(newEnemies);
    }

    public void draw(Canvas canvas) {
        //draw enemies
        for (Enemy enemy : enemies) {
            enemy.draw(canvas);
        }

    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public Enemy spawnEnemy(int type) {

        int yStart = r.nextInt((9*(Constants.SCREEN_HEIGHT/10)) - 200) + 200;

        Enemy newEnemy;

        switch(type) {
            case 1:
                newEnemy = new Mosquito(new Rect(currX, yStart, currX+Mosquito.WIDTH, yStart+Mosquito.HEIGHT));
                break;
            case 2:
                newEnemy = new Crab(new Rect(currX, yStart, currX+Crab.WIDTH, yStart+Crab.HEIGHT));
                break;
            case 3:
                newEnemy = new Cloud(new Rect(currX, yStart, currX+Cloud.WIDTH, yStart+Cloud.HEIGHT));
                break;
            default:
                newEnemy = new Mosquito(new Rect(currX, yStart, currX+Mosquito.WIDTH, yStart+Mosquito.HEIGHT));
                break;
        }

        return newEnemy;
    }
}
