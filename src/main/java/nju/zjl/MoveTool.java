package nju.zjl;

import java.util.List;

import javafx.scene.input.MouseEvent;

public class MoveTool implements MouseHandler {
    public MoveTool(List<AbstractItem> items, List<AbstractItem> selectedItems){
        this.items = items;
        this.selectedItems = selectedItems;
        lastOver = null;
        lastX = 0;
        lastY = 0;
    }
    
    @Override
    public boolean mouseMoved(MouseEvent evt){
        AbstractItem temp = lastOver;
        lastOver = overWhich(evt.getX(), evt.getY(), items);
        return lastOver != temp;
    }

    @Override
    public boolean mousePressed(MouseEvent evt){
        double x = evt.getX();
        double y = evt.getY();
        AbstractItem i = overWhich(x, y, items);
        if(evt.isControlDown()){
            if(i != null){
                if(selectedItems.contains(i)){
                    selectedItems.remove(i);
                    i.setSelected(false);
                }
                else{
                    selectedItems.add(i);
                    i.setSelected(true);
                }
                lastX = x;
                lastY = y;
                return true;
            }
        }
        else{
            if(i == null){
                if(!selectedItems.isEmpty()){
                    selectedItems.stream().forEach(it -> it.setSelected(false));
                    selectedItems.clear();
                    return true;
                }
            }
            else{
                selectedItems.stream().forEach(it -> it.setSelected(false));
                selectedItems.clear();
                i.setSelected(true);
                selectedItems.add(i);
                lastX = x;
                lastY = y;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseDragged(MouseEvent evt){
        double x = evt.getX();
        double y = evt.getY();
        if(!selectedItems.isEmpty()){
            selectedItems.stream().forEach(it -> it.translate(x - lastX, y - lastY));
            lastX = x;
            lastY = y;
            return true;
        }
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
    protected AbstractItem lastOver;
    protected double lastX;
    protected double lastY;
}
