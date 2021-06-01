package nju.zjl;

public class Vector2D implements Cloneable {
    public Vector2D(){
        x = 0;
        y = 0;
    }

    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public Vector2D clone() throws CloneNotSupportedException {
        return (Vector2D)super.clone();
    }

    public void translate(double dx, double dy){
        x += dx;
        y += dy;
    }

    public double x;
    public double y;
}
