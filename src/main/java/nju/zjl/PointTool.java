package nju.zjl;

public class PointTool extends MouseHandler {
    public PointTool(){
        tempPoint = new Point(0, 0);
    }

    @Override
    public void setup(){
        tempItems.add(tempPoint);
    }

    @Override
    public void cleanup(){
        tempItems.remove(tempPoint);
    }

    @Override
    public boolean mouseMoved(double x, double y){
        Point p = overWhich(x, y, points);
        if(p == null){
            tempPoint.getPos().x = x;
            tempPoint.getPos().y = y;
        }
        else{
            tempPoint.getPos().x = p.getPos().x;
            tempPoint.getPos().y = p.getPos().y;
        }
        return true;
    }

    @Override
    public boolean mouseClicked(double x, double y){
        Point p = overWhich(x, y, points);
        if(p == null){
            tempPoint.getPos().x = x;
            tempPoint.getPos().y = y;
            Point po = new Point(x, y);
            po.setSelected(true);
            points.add(po);
            setSelectedItem.accept(po);
        }
        else{
            tempPoint.getPos().x = p.getPos().x;
            tempPoint.getPos().y = p.getPos().y;
            p.setSelected(true);
            setSelectedItem.accept(p);
        }
        return true;
    }

    private Point tempPoint;
}
