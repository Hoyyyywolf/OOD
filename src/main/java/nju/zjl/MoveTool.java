package nju.zjl;

import java.util.List;

import javafx.scene.input.MouseEvent;

public class MoveTool implements MouseHandler {
    public MoveTool(List<AbstractItem> items, List<AbstractItem> selectedItems){
        this.items = items;
        this.selectedItems = selectedItems;
    }
    
    @Override
    public boolean mouseMoved(MouseEvent evt){
        AbstractItem i = overWhich(evt.getX(), evt.getY(), items);
        return i != null;
    }

    @Override
    public boolean mousePressed(MouseEvent evt){
        double x = evt.getX();
        double y = evt.getY();
        AbstractItem i = overWhich(x, y, items);
        System.out.println("press");
        return false;
    }

    @Override
    public boolean mouseDragged(MouseEvent evt){
        System.out.println("drag");
        return false;
    }

    protected AbstractItem overWhich(double x, double y, List<AbstractItem> items){
        boolean flag = true;
        AbstractItem ret = null;
        for(AbstractItem i : items){
            i.setOvered(false);
            if(flag && i.hangOver(x, y)){
                i.setOvered(true);
                ret = i;
                flag = false;
            }
        }
        return ret;
    }

    protected List<AbstractItem> items;
    protected List<AbstractItem> selectedItems;
}
