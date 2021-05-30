package nju.zjl;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Point extends AbstractItem {
    public Point(double x, double y){
        super();
        pos = new Vector2D(x, y);
    }

    @Override
    public void draw(GraphicsContext gc){
        gc.setFill(Color.RED);
        gc.fillOval(pos.x - Constants.POINT_RADIUS, pos.y - Constants.POINT_RADIUS, Constants.POINT_RADIUS*2, Constants.POINT_RADIUS*2);
    }

    @Override
    public boolean hangOver(double x, double y){
        double dx = Math.abs(x - pos.x);
        double dy = Math.abs(y - pos.y);
        return dx <= Constants.POINT_RADIUS && dy <= Constants.POINT_RADIUS;
    }

    @Override
    public void translate(double dx, double dy){
        pos.translate(dx, dy);
    }

    public Vector2D getPos(){
        return pos;
    }

    private Vector2D pos;
}
