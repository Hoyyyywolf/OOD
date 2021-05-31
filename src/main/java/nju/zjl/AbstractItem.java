package nju.zjl;

import javafx.scene.canvas.GraphicsContext;

public abstract class AbstractItem {
    protected boolean overed;
    protected boolean selected;
    
    public abstract void draw(GraphicsContext gc);
    public abstract boolean hangOver(double x, double y);
    public abstract void translate(double dx, double dy);

    protected AbstractItem(){
        overed = false;
        selected = false;
    }

    public void setOvered(boolean val){
        overed = val;
    }

    public void setSelected(boolean val){
        selected = val;
    }
}
