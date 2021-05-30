package nju.zjl;

import java.util.List;
import java.util.function.Consumer;

public abstract class MouseHandler {
    public abstract void setup();
    public abstract void cleanup();
    public abstract boolean mouseMoved(double x, double y);
    public abstract boolean mouseClicked(double x, double y);

    protected <T extends AbstractItem> T overWhich(double x, double y, List<T> items){
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

    protected static List<Point> points;
    protected static List<AbstractItem> items;
    protected static List<AbstractItem> tempItems;
    protected static Consumer<AbstractItem> setSelectedItem;

    public static void init(List<Point> ps, List<AbstractItem> is, List<AbstractItem> tis, Consumer<AbstractItem> ssi){
        points = ps;
        items = is;
        tempItems = tis;
        setSelectedItem = ssi;
    }
}
