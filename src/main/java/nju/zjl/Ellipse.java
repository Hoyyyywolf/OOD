package nju.zjl;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ellipse extends AbstractBinaryItem {
    public Ellipse(double x1, double y1, double x2, double y2){
        super(x1, y1, x2, y2);
    }

    @Override
    public void drawItem(GraphicsContext gc){
        gc.setStroke(Color.BLACK);
        gc.strokeOval(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y), Math.abs(v1.x - v2.x), Math.abs(v1.y - v2.y));
    }

    @Override
    public boolean hangOver(double x, double y){
        if(boundingRect().inside(x, y)){
            x -= (v1.x + v2.x)/2;
            y -= (v1.y + v2.y)/2;
            double a = (v1.x - v2.x)/2;
            double b = (v1.y - v2.y)/2;
            double offset = x*x/a/a + y*y/b/b - 1;
            return Math.abs(offset) <= Constants.LINE_WIDTH*(2*Math.abs(x) + Constants.LINE_WIDTH)/a/a + Constants.LINE_WIDTH*(2*Math.abs(y) + Constants.LINE_WIDTH)/b/b;
        }
        return false;
    }
}
