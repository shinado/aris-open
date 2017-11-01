package indi.ss.pipes.snake;

public class Maze {

    public int width;
    public int height;

    public Maze(int width, int height){
        this.width = width;
        this.height = height;
    }

    public boolean hitWall(Point point){
        return point.x < 0
                || point.y < 0
                || point.x >= width
                || point.y >= height;
    }
}
