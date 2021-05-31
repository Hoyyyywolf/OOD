package nju.zjl;

import java.util.List;

public interface MouseHandler {
    boolean mouseMoved(double x, double y);
    boolean mouseClicked(double x, double y);

    default <T extends AbstractItem> T overWhich(double x, double y, List<T> items){
        boolean flag = true;
        T ret = null;
        for(T i : items){
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
