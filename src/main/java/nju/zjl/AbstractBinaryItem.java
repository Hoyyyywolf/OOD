package nju.zjl;

public abstract class AbstractBinaryItem extends AbstractItem {
    protected AbstractBinaryItem(double x1, double y1, double x2, double y2){
        super();
        v1 = new Vector2D(x1, y1);
        v2 = new Vector2D(x2, y2);
    }

    @Override
    public Rect boundingRect(){
        double x = Math.min(v1.x, v2.x);
        double y = Math.min(v1.y, v2.y);
        return new Rect(x - 1, y - 1, Math.abs(v1.x - v2.x) + 2, Math.abs(v1.y - v2.y) + 2);
    }

    @Override
    public void translate(double dx, double dy){
        v1.translate(dx, dy);
        v2.translate(dx, dy);
    }

    @Override
    public AbstractBinaryItem clone() throws CloneNotSupportedException {
        AbstractBinaryItem ret = (AbstractBinaryItem)super.clone();
        ret.v1 = this.v1.clone();
        ret.v2 = this.v2.clone();
        return ret;
    }

    public void setVec2(double x, double y){
        v2.x = x;
        v2.y = y;
    }

    protected Vector2D v1;
    protected Vector2D v2;
}
