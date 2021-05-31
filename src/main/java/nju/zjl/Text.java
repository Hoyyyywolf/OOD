package nju.zjl;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;

public class Text extends AbstractItem {
    public Text(double x, double y, String text){
        super();
        pivot = new Vector2D(x, y);
        content = text;
    }

    @Override
    public void drawItem(GraphicsContext gc){
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(content, pivot.x, pivot.y, Constants.TEXT_WIDTH);
    }

    @Override
    public boolean hangOver(double x, double y){
        return boundingRect().inside(x, y);
    }

    @Override
    public Rect boundingRect(){
        return new Rect(pivot.x - 4, pivot.y - Constants.TEXT_HEIGHT/2, Constants.TEXT_WIDTH, Constants.TEXT_HEIGHT);
    }

    @Override
    public void translate(double dx, double dy){
        pivot.translate(dx, dy);
    }

    Vector2D pivot;
    String content;
}
