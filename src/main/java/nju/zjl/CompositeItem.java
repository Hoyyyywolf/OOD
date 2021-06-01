package nju.zjl;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public class CompositeItem extends AbstractItem {
    public CompositeItem(List<AbstractItem> children){
        super();
        this.children = new LinkedList<>(children);

        double xmin = Double.MAX_VALUE, ymin = Double.MAX_VALUE;
        double xmax = Double.MIN_VALUE, ymax = Double.MIN_VALUE;
        for(AbstractItem i : children){
            xmin = Math.min(xmin, i.boundingRect().x);
            ymin = Math.min(ymin, i.boundingRect().y);
            xmax = Math.max(xmax, i.boundingRect().x + i.boundingRect().w);
            ymax = Math.max(ymax, i.boundingRect().y + i.boundingRect().h);
        }
        x = xmin;
        y = ymin;
        w = xmax - xmin;
        h = ymax - ymin;
    }

    public void drawItem(GraphicsContext gc){
        for(AbstractItem i : children){
            i.drawItem(gc);
        }
    }

    public boolean hangOver(double x, double y){
        return boundingRect().inside(x, y);
    }

    public Rect boundingRect(){
        return new Rect(x, y, w, h);
    }

    public void translate(double dx, double dy){
        children.stream().forEach(i -> i.translate(dx, dy));
        x += dx;
        y += dy;
    }

    protected List<AbstractItem> children;
    protected double x, y;
    protected double w, h;
}
