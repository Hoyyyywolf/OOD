package nju.zjl;

import java.util.List;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class BinaryItemTool implements MouseHandler {
    public BinaryItemTool(List<AbstractItem> items, List<AbstractItem> tempItems, Recorder recorder, BinaryItemSupplier supplier){
        clicked = false;
        this.recorder = recorder;
        this.items = items;
        this.tempItems = tempItems;
        this.supplier = supplier;
    }

    @Override
    public boolean mouseMoved(MouseEvent evt){
        double x = evt.getX();
        double y = evt.getY();
        if(clicked){
            if(evt.isShiftDown()){
                x -= x1;
                y -= y1;
                if(Math.abs(x) < Math.abs(y)){
                    y = Math.signum(y)*Math.abs(x);
                }
                else{
                    x = Math.signum(x)*Math.abs(y);
                }
                temp.setVec2(x1 + x, y1 + y);
            }       
            else{
                temp.setVec2(x, y);
            }     
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseClicked(MouseEvent evt){
        if(!evt.getButton().equals(MouseButton.PRIMARY)){
            return false;
        }

        double x = evt.getX();
        double y = evt.getY();
        if(clicked){
            clicked = false;
            recorder.addRecord(new CreateRecord(temp, items));
            items.add(temp);
            tempItems.clear();
        }
        else{
            clicked = true;
            x1 = x;
            y1 = y;
            temp = supplier.get(x, y, x, y);
            tempItems.add(temp);
        }

        return true;
    }

    protected double x1;
    protected double y1;
    protected boolean clicked;
    protected Recorder recorder;
    protected AbstractBinaryItem temp;
    protected List<AbstractItem> items;
    protected List<AbstractItem> tempItems;
    protected BinaryItemSupplier supplier;
}

class CreateRecord implements Record {
    CreateRecord(AbstractItem i, List<AbstractItem> items){
        this.i= i;
        this.items = items;
    }

    @Override
    public void undo(){
        items.remove(i);
    }

    private AbstractItem i;
    private List<AbstractItem> items;
}

@FunctionalInterface
interface BinaryItemSupplier {
    AbstractBinaryItem get(double x1, double y1, double x2, double y2);
}
