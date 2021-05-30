package nju.zjl;

import java.util.List;

public interface MouseHandler {
    public void init(List<AbstractItem> tempItems);
    public boolean mouseMoved(double x, double y, List<AbstractItem> items, List<AbstractItem> tempItems, AbstractItem selectedItem);
    public boolean mouseClicked(double x, double y, List<AbstractItem> items, List<AbstractItem> tempItems, AbstractItem selectedItem);

    public static AbstractItem overWhich(double x, double y, List<AbstractItem> items){
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
}
