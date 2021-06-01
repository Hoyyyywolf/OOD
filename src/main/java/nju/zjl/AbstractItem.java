package nju.zjl;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class AbstractItem implements Cloneable {
    protected AbstractItem(){
        overed = false;
        selected = false;
    }

    @Override
    public AbstractItem clone() throws CloneNotSupportedException {
        return (AbstractItem)super.clone();
    }

    public void draw(GraphicsContext gc){
        drawItem(gc);
        gc.setLineWidth(Constants.DASH_LINE_WIDTH);
        gc.setLineDashes(Constants.DASHES);
        Rect rec = boundingRect();
        if(overed){
            gc.setStroke(Color.RED);
            gc.strokeRect(rec.x, rec.y, rec.w, rec.h);
        }
        else if(selected){
            gc.setStroke(Color.BLACK);
            gc.strokeRect(rec.x, rec.y, rec.w, rec.h);
        }
        gc.setLineWidth(Constants.LINE_WIDTH);
        gc.setLineDashes();
    }

    public void setOvered(boolean val){
        overed = val;
    }

    public void setSelected(boolean val){
        selected = val;
    }

    protected static boolean overLine(double x, double y, Vector2D v1, Vector2D v2){
        double a = v2.y - v1.y;
        double b = v1.x - v2.x;
        double offset = a*x + b*y -v1.x*v2.y + v2.x*v1.y;
        return Math.abs(offset) <= Constants.LINE_WIDTH*(Math.abs(a) + Math.abs(b));
    }

    public abstract void drawItem(GraphicsContext gc);
    public abstract boolean hangOver(double x, double y);
    public abstract Rect boundingRect();
    public abstract void translate(double dx, double dy);

    protected boolean overed;
    protected boolean selected;
}
