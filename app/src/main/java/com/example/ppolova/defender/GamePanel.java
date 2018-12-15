package com.example.ppolova.defender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;

    private static GamePanel INSTANCE;

    //rectangle to draw GAME OVER text into
    private Rect r = new Rect();

    private Player player;
    private Point playerPoint;

    private EnemyManager enemyManager;
    private ScoreManager scoreManager;

    private List<Star> stars = new ArrayList<Star>();

    private List<Shot> shots = new ArrayList<Shot>();
    private List<EnemyShot> enemyShots = new ArrayList<EnemyShot>();

    //determines if user is moving the player
    private boolean movingPlayer = false;

    private boolean gameOver = false;
    private long gameOverTime;

    public String playerName;
    public int difficulty;

    public GamePanel(Context context) {
        super(context);

        INSTANCE = this;

        getHolder().addCallback(this);

        //sets current context
        Constants.CURRENT_CONTEXT = context;

        thread = new MainThread(getHolder(), this);

        //spawn a player at the beginning and move it to proper location
        player = new Player(new Rect(100, 100, 100+Player.WIDTH, 100+Player.HEIGHT), Color.rgb(96, 128, 145));
        playerPoint = new Point(Constants.SCREEN_WIDTH/4, Constants.SCREEN_HEIGHT/2);
        player.update(playerPoint);

        enemyManager = new EnemyManager();
        scoreManager = new ScoreManager(context);

        // get user's name and selected difficulty
        Preferencies preferencies = new Preferencies(context);
        this.playerName = preferencies.getPlayerName();
        this.difficulty = preferencies.getDifficulty();

        // spawn stars
        int numberOfStars = 100;
        for (int i = 0; i < numberOfStars; i++) {
            stars.add(new Star());
        }

        setFocusable(true);
    }

    public void gameOver() {
        gameOver = true;

        // if score is greater than 0, save it
        if (player.getScore() > 0) {scoreManager.addData(playerName, player.getScore());}
        gameOverTime = System.currentTimeMillis();
    }

    //reset the game after GAME OVER
    public void reset() {
        if (!gameOver) return;
        enemyManager = new EnemyManager();
        movingPlayer = false;
        player.reset();
        playerPoint = new Point(Constants.SCREEN_WIDTH/4, Constants.SCREEN_HEIGHT/2);
        player.update(playerPoint);
        shots.clear();
        enemyShots.clear();
        gameOver = false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        while(retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                //game is running and user touched the Player
                if (!gameOver && player.getTouchRectangle().contains((int)event.getX(), (int)event.getY())) {
                    movingPlayer = true;
                } else if (!gameOver && (event.getX() > Constants.SCREEN_WIDTH/2)) {
                    Shot newShot = new Shot(player.getRectangle().right, player.getRectangle().centerY());
                    this.shots.add(newShot);
                }
                //game is over and 2 seconds passed
                if (gameOver && System.currentTimeMillis() - gameOverTime >= 2000) {
                    reset();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!gameOver && movingPlayer)
                    if (event.getY() < 200) {
                        playerPoint.set(Constants.SCREEN_WIDTH/4, 200);
                    } else if (event.getY() > 9*(Constants.SCREEN_HEIGHT/10)) {

                    } else {
                        playerPoint.set(Constants.SCREEN_WIDTH / 4, (int) event.getY());
                    }
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }

        return true;
    }

    public void update() {
        if (!gameOver) {

            for (Star star : stars) {
                star.update();
            }

            for (Shot shot : shots) {
                if (!shot.toBeDeleted) shot.update();
            }

            for (EnemyShot shot : enemyShots) {
                if (!shot.toBeDeleted) shot.update();
            }

            if (player.getHealth() <= 0) {
                gameOver();
            }

            // safely remove shots
            ListIterator<Shot> iter = shots.listIterator();
            while(iter.hasNext()){
                if(iter.next().toBeDeleted){
                    iter.remove();
                }
            }

            // safely remove enemy shots
            ListIterator<EnemyShot> iter2 = enemyShots.listIterator();
            while(iter2.hasNext()){
                if(iter2.next().toBeDeleted){
                    iter2.remove();
                }
            }


            player.update(playerPoint);

            // if enemy collided with player, game is over
            enemyManager.update();
            if (enemyManager.playerCollision(player)) {
                player.setHealth(0);
                gameOver();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);

        canvas.drawColor(Color.BLACK);

        for (Star star : stars) {
            star.draw(canvas);
        }

        for (Shot shot : shots) {
            if (!shot.toBeDeleted) shot.draw(canvas);
        }

        for (EnemyShot shot : enemyShots) {
            if (!shot.toBeDeleted) shot.draw(canvas);
        }

        player.draw(canvas);
        enemyManager.draw(canvas);

        if(gameOver) {
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            drawCenterText(canvas, paint, "GAME OVER");

            paint.setTextSize(70);
            drawBottomText(canvas, paint, "TAP OR SHAKE TO START AGAIN");

        }

        //draw score
        Paint paint = new Paint();
        paint.setTextSize(80);
        paint.setColor(Color.MAGENTA);
        canvas.drawText("Score: " + player.getScore(), 50, 50 + paint.descent() - paint.ascent(), paint);
        canvas.drawText("Health: " + player.getHealth(), 700, 50 + paint.descent() - paint.ascent(), paint);
    }

    //draw text in the center of screen
    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }

    private void drawBottomText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom + 150;
        canvas.drawText(text, x, y, paint);
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public static GamePanel getInstance() {
        return INSTANCE;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Shot> getShots() {
        return shots;
    }

    public MainThread getThread() {
        return thread;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public List<EnemyShot> getEnemyShots() {
        return enemyShots;
    }
}
