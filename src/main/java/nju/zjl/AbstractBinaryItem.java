package nju.zjl;

public abstract class AbstractBinaryItem extends AbstractItem {
    protected AbstractBinaryItem(double x1, double y1, double x2, double y2){
        super();
        v1 = new Vector2D(x1, y1);
        v2 = new Vector2D(x2, y2);
    }

    @Override
    public Rect boundingRect(){
        return new Rect((int)v1.x, (int)v1.y, (int)Math.abs(v1.x - v2.x), (int)Math.abs(v1.y - v2.y));
    }

    @Override
    public void translate(double dx, double dy){
        v1.translate(dx, dy);
        v2.translate(dx, dy);
    }

    public void setVec2(double x, double y){
        v2.x = x;
        v2.y = y;
    }

    protected Vector2D v1;
    protected Vector2D v2;
}
