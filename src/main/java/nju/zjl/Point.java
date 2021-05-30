package nju.zjl;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Point extends AbstractItem {
    public Point(double x, double y){
        v1 = new Vector2D(x, y);
    }

    @Override
    public void draw(GraphicsContext gc){
        gc.setFill(Color.RED);
        gc.fillOval(v1.x - Constants.POINT_RADIUS, v1.y - Constants.POINT_RADIUS, Constants.POINT_RADIUS*2, Constants.POINT_RADIUS*2);
    }

    @Override
    public boolean hangOver(double x, double y){
        double dx = Math.abs(x - v1.x);
        double dy = Math.abs(y - v1.y);
        return dx <= Constants.POINT_RADIUS && dy <= Constants.POINT_RADIUS;
    }

    @Override
    public void translate(double dx, double dy){
        v1.translate(dx, dy);
    }

    private Vector2D v1;
}
