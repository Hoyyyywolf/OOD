package nju.zjl;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Triangle extends AbstractBinaryItem {
    public Triangle(double x1, double y1, double x2, double y2){
        super(x1, y1, x2, y2);
    }

    @Override
    public void drawItem(GraphicsContext gc){
        double[] xPoints = new double[3];
        double[] yPoints = new double[3];
        xPoints[0] = (v1.x + v2.x)/2;
        yPoints[0] = Math.min(v1.y, v2.y);
        xPoints[1] = Math.min(v1.x, v2.x);
        yPoints[1] = Math.max(v1.y, v2.y);
        xPoints[2] = Math.max(v1.x, v2.x);
        yPoints[2] = Math.max(v1.y, v2.y);
        gc.setStroke(Color.BLACK);
        gc.strokePolygon(xPoints, yPoints, 3);
    }

    @Override
    public boolean hangOver(double x, double y){
        if(boundingRect().inside(x, y)){
            double[] xPoints = new double[3];
            double[] yPoints = new double[3];
            xPoints[0] = (v1.x + v2.x)/2;
            yPoints[0] = Math.min(v1.y, v2.y);
            xPoints[1] = Math.min(v1.x, v2.x);
            yPoints[1] = Math.max(v1.y, v2.y);
            xPoints[2] = Math.max(v1.x, v2.x);
            yPoints[2] = Math.max(v1.y, v2.y);
            for(int i = 0; i < 3; i++){
                if(AbstractItem.overLine(x, y, new Vector2D(xPoints[i], yPoints[i]), new Vector2D(xPoints[(i + 1)%3], yPoints[(i + 1)%3]))){
                    return true;
                }
            }
        }
        return false;
    }
}
