package nju.zjl;

import java.util.List;

public class PointTool implements MouseHandler {
    public PointTool(){
        point = new Point(0, 0);
    }

    @Override
    public void init(List<AbstractItem> tempItems){
        tempItems.add(point);
    }

    @Override
    public boolean mouseMoved(double x, double y, List<AbstractItem> items, List<AbstractItem> tempItems, AbstractItem selectedItem){
        AbstractItem it = MouseHandler.overWhich(x, y, items);
        if(it instanceof Point){
            
        }
    }

    private Point point;
}
