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

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;

    //rectangle to draw GAME OVER text into
    private Rect r = new Rect();

    private Player player;
    private Point playerPoint;
    public static final int playerXPosition = Constants.SCREEN_WIDTH/4;

    private EnemyManager enemyManager;

    //determines if user is moving the player
    private boolean movingPlayer = false;

    private boolean gameOver = false;
    private long gameOverTime;

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);

        //sets current context
        Constants.CURRENT_CONTEXT = context;

        thread = new MainThread(getHolder(), this);

        //spawn a player at the beginning and move it to proper location
        player = new Player(new Rect(100, 100, 100+Player.WIDTH, 100+Player.HEIGHT), Color.rgb(96, 128, 145));
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);

        enemyManager = new EnemyManager();

        setFocusable(true);
    }

    //reset the game after GAME OVER
    public void reset() {
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);
        enemyManager = new EnemyManager();
        movingPlayer = false;
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
        while(true) {
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
                //System.out.println(player.getRectangle().centerX());
                //System.out.println(player.getRectangle().centerY());
                System.out.println(player.getRectangle().width());
                System.out.println(player.getRectangle().height());
                //System.out.println(event.getX());
                //System.out.println(event.getY());
                if (!gameOver && player.getRectangle().contains((int)event.getX(), (int)event.getY())) {
                    movingPlayer = true;
                }
                if (gameOver && System.currentTimeMillis() - gameOverTime >= 2000) {
                    reset();
                    gameOver = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!gameOver && movingPlayer)
                    playerPoint.set((int)event.getX(), (int)event.getY());
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }

        return true;
        //return super.onTouchEvent(event);
    }

    public void update() {
        if (!gameOver) {
            player.update(playerPoint);
            enemyManager.update();
            if (enemyManager.playerCollision(player)) {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);

        canvas.drawColor(Color.BLACK);

        player.draw(canvas);
        enemyManager.draw(canvas);

        if(gameOver) {
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            drawCenterText(canvas, paint, "GAME OVER");

        }
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

}
