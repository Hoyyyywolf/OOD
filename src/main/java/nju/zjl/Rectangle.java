package nju.zjl;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends AbstractBinaryItem {
    public Rectangle(double x1, double y1, double x2, double y2){
        super(x1, y1, x2, y2);
    }

    @Override
    public void drawItem(GraphicsContext gc){
        gc.setStroke(Color.BLACK);
        gc.strokeRect(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y), Math.abs(v1.x - v2.x), Math.abs(v1.y - v2.y));
    }

    @Override
    public boolean hangOver(double x, double y){
        if(boundingRect().inside(x, y)){
            double[] xPoints = new double[4];
            double[] yPoints = new double[4];
            xPoints[0] = v1.x;
            yPoints[0] = v1.y;
            xPoints[1] = v1.x;
            yPoints[1] = v2.y;
            xPoints[2] = v2.x;
            yPoints[2] = v2.y;
            xPoints[3] = v2.x;
            yPoints[3] = v1.y;
            for(int i = 0; i < 4; i++){
                if(AbstractItem.overLine(x, y, new Vector2D(xPoints[i], yPoints[i]), new Vector2D(xPoints[(i + 1)%4], yPoints[(i + 1)%4]))){
                    return true;
                }
            }
        }
        return false;
    }
}
