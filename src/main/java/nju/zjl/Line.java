package nju.zjl;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line extends AbstractBinaryItem {
    public Line(double x1, double y1, double x2, double y2){
        super(x1, y1, x2, y2);
    }

    @Override
    public void drawItem(GraphicsContext gc){
        gc.setStroke(Color.BLACK);
        gc.strokeLine(v1.x, v1.y, v2.x, v2.y);
    }

    @Override
    public boolean hangOver(double x, double y){
        return AbstractItem.overLine(x, y, v1, v2);
    }
}
