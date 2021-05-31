package nju.zjl;

import java.util.List;

import javafx.scene.input.MouseEvent;

public class LineTool extends BinaryItemTool {
    public LineTool(List<AbstractItem> items, List<AbstractItem> tempItems){
        super(items, tempItems, Line::new);
    }
    
    @Override
    public boolean mouseMoved(MouseEvent evt){
        double x = evt.getX();
        double y = evt.getY();
        if(clicked){
            if(evt.isShiftDown()){
                x -= x1;
                y -= y1;
                if(Math.abs(x) < 0.414*Math.abs(y)){
                    x = 0;
                }
                else if(Math.abs(y) < 0.414*Math.abs(x)){
                    y = 0;
                }
                else if(Math.abs(x) < Math.abs(y)){
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
}
