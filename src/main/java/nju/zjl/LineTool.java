package nju.zjl;

import java.util.List;

public class LineTool implements MouseHandler {
    public LineTool(List<AbstractItem> items, List<AbstractItem> tempItems){
        clicked = false;
        this.items = items;
        this.tempItems = tempItems;
    }

    @Override
    public boolean mouseMoved(double x, double y){
        if(clicked){
            tempLine.setVec2(x, y);
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseClicked(double x, double y){
        if(clicked){
            clicked = false;
            tempLine.setVec2(x, y);
            items.add(tempLine);
            tempItems.clear();
        }
        else{
            clicked = true;
            tempLine = new Line(x, y, x, y);
            tempItems.add(tempLine);
        }

        return true;
    }

    boolean clicked;
    protected Line tempLine;
    protected List<AbstractItem> items;
    protected List<AbstractItem> tempItems;
}
