package nju.zjl;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MoveTool implements MouseHandler {
    public MoveTool(List<AbstractItem> items, List<AbstractItem> tempItems, List<AbstractItem> selectedItems){
        this.items = items;
        this.tempItems = tempItems;
        this.selectedItems = selectedItems;
        lastOver = null;
        lastX = 0;
        lastY = 0;
        pressSpace = false;
        boxSelection = null;
        beginCopy = false;
    }
    
    @Override
    public boolean mouseMoved(MouseEvent evt){
        AbstractItem t = lastOver;
        lastOver = overWhich(evt.getX(), evt.getY(), items);
        return lastOver != t;
    }

    @Override
    public boolean mousePressed(MouseEvent evt){
        double x = evt.getX();
        double y = evt.getY();
        lastX = x;
        lastY = y;
        AbstractItem i = overWhich(x, y, items);
        if(i == null){
            pressSpace = true;
            if(!selectedItems.isEmpty()){
                selectedItems.forEach(it -> it.setSelected(false));
                selectedItems.clear();
                return true;
            }
        }
        else{
            pressSpace = false;
            selectedItems.forEach(it -> it.setSelected(false));
            selectedItems.clear();
            i.setSelected(true);
            selectedItems.add(i);
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(MouseEvent evt){
        double x = evt.getX();
        double y = evt.getY();
        if(pressSpace){
            if(boxSelection == null){
                boxSelection = new BoxSelection(lastX, lastY, x, y);
                tempItems.add(boxSelection);
                updateBoxSelection();
            }
            else{
                boxSelection.setVec2(x, y);
                updateBoxSelection();
            }
        }
        else{
            if(evt.isAltDown() && !beginCopy){
                beginCopy = true;
                List<AbstractItem> newItems = new LinkedList<>();
                try{
                    for(AbstractItem i : selectedItems){
                        newItems.add(i.clone());
                        i.setSelected(false);
                    }
                    items.addAll(newItems);
                    selectedItems.clear();
                    selectedItems.addAll(newItems);
                }catch(CloneNotSupportedException e){
                    e.printStackTrace();
                    selectedItems.clear();
                }
            }
            selectedItems.forEach(i -> i.translate(x - lastX, y - lastY));
            lastX = x;
            lastY = y;
        }
        return true;
    }

    @Override
    public boolean mouseReleased(MouseEvent evt){
        if(boxSelection != null){
            updateBoxSelection();
            tempItems.clear();
            boxSelection = null;
            return true;
        }
        beginCopy = false;
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

    protected void updateBoxSelection(){
        selectedItems.clear();
        Rect rec = boxSelection.boundingRect();
        for(AbstractItem i : items){
            if(rec.hasIntersection(i.boundingRect())){
                i.setSelected(true);
                selectedItems.add(i);
            }
            else{
                i.setSelected(false);
            }
        }
    }

    protected List<AbstractItem> items;
    protected List<AbstractItem> tempItems;
    protected List<AbstractItem> selectedItems;
    protected AbstractItem lastOver;
    protected double lastX;
    protected double lastY;
    protected boolean pressSpace;
    protected AbstractBinaryItem boxSelection;
    protected boolean beginCopy;
}

class BoxSelection extends AbstractBinaryItem {
    public BoxSelection(double x1, double y1, double x2, double y2){
        super(x1, y1, x2, y2);
    }
    
    @Override
    public void drawItem(GraphicsContext gc){
        gc.setFill(Color.GRAY);
        gc.setGlobalAlpha(0.3);
        gc.fillRect(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y), Math.abs(v1.x - v2.x), Math.abs(v1.y - v2.y));
        gc.setGlobalAlpha(1.0);
    }

    @Override
    public boolean hangOver(double x, double y){
        return false;
    }
}