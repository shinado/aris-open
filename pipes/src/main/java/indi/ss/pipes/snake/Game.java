package indi.ss.pipes.snake;

import android.util.Log;

import java.util.Random;

class Game {

    private Console console;
    static final byte EMPTY = 0;
    private static final byte SOLID = 1;
    private Maze maze;
    private Snake snake;
    private Point dot;
    private byte[][] matrix;
    private static final int INTERVAL_SNAKE_SIZE = 10;
    private static final int INTERVAL_SPEED_UP = 10;
    private static final int INTERVAL_MAX = 350;
    private static final int INTERVAL_MIN = 250;
//    private int interval = ;
    private boolean running = false;

    public void create(Maze maze, Snake snake, Console console){
        this.maze = maze;
        this.snake =snake;
        this.console = console;

        initMatrix(maze);
        for (Point point : snake.getBody()){
            add(point);
        }

        dot = getNewDot();
        add(dot);
    }

    public void start(){
        running = true;
        new GameThread().start();
    }

    public void stop(){
        running = false;
    }

    public void end(){
        running = false;
    }

    public int getScore(){
        return snake.body.size();
    }

    public Maze getMaze() {
        return maze;
    }

    private class GameThread extends Thread{
        @Override
        public void run(){
            while (running){
                try {
                    int interval = Math.max(INTERVAL_MIN,
                            INTERVAL_MAX - (snake.body.size() / INTERVAL_SNAKE_SIZE)
                                    * INTERVAL_SPEED_UP);
                    sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Snake previous = snake.clone();
                Point next = snake.crawl(dot);
                if (next == null){
                    //hit itself
                    console.die();
                    return;
                }

                if (next.equals(dot)){
                    dot = getNewDot();
                    add(dot);
                }
                if (maze.hitWall(next)){
                    console.die();
                    return;
                }
                onMove(previous, next);
            }
        }
    }

    private Point getNewDot(){
        Point point = getRandomPoint();
        while (snake.isPointPartOfBody(point)){
            point = getRandomPoint();
        }
        Log.d("snake", "dot:"+point.x + "," + point.y);
        return point;
    }

    private Point getRandomPoint(){
        Point point = new Point(
                Math.abs(new Random().nextInt() % maze.width),
                Math.abs(new Random().nextInt() % maze.height)
        );
        return point;
    }

    interface GameListener{
        void onMove(Snake snake, Point next);
        void onNewDot(Point point);
    }

    public Snake getSnake(){
        return snake;
    }

    private void initMatrix(Maze maze){
        matrix = new byte[maze.height][maze.width];
        for (byte[] row : matrix){
            for (int i=0; i<row.length; i++){
                row[i] += EMPTY;
            }
        }
    }

    private void onMove(Snake snake, Point next) {
        //previous
        Point previousTail = snake.getTail();
        remove(previousTail);
        add(next);
        console.draw(matrix);
    }

    private void remove(Point point){
        matrix[point.y][point.x] = EMPTY;
    }

    private void add(Point point){
        matrix[point.y][point.x] = SOLID;
    }

}
