package indi.ss.pipes.snake;

public class Point{
    public int x;
    public int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof Point){
            Point another = (Point) obj;
            return another.x == x && another.y == y;
        }
        return false;
    }
}
