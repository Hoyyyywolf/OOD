package nju.zjl;

public class Rect {
    public Rect(double x, double y, double w, double h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean inside(double x0, double y0){
        x0 -= x;
        y0 -= y;
        return x0 >= 0 && x0 <= w && y0 >= 0 && y0 <= h;
    }

    public double x;
    public double y;
    public double w;
    public double h;
}
