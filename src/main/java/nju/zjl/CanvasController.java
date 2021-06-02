package nju.zjl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.StrokeLineCap;

public class CanvasController {
    public CanvasController(Canvas canvas){
        this.canvas = canvas;
        recorder = new Recorder(20);
        items = new LinkedList<>();
        tempItems = new LinkedList<>();
        selectedItems = new LinkedList<>();
        state = "";
        handler = new MouseHandler(){};
        handlerMap = new HashMap<>();

        canvas.getGraphicsContext2D().setLineCap(StrokeLineCap.ROUND);
        initHandler();
        initTool();
    }

    public void initTool(){
        handlerMap.put("line", new LineTool(items, tempItems, recorder));
        handlerMap.put("move", new MoveTool(items, tempItems, selectedItems, recorder));
        handlerMap.put("triangle", new BinaryItemTool(items, tempItems, recorder, Triangle::new));
        handlerMap.put("rectangle", new BinaryItemTool(items, tempItems, recorder, Rectangle::new));
        handlerMap.put("ellipse", new BinaryItemTool(items, tempItems, recorder, Ellipse::new));
        handlerMap.put("text", new TextTool(items, recorder));
    }

    public void changeState(String s){
        if(!state.equals(s)){
            state = s;
            handler = handlerMap.get(s);
            tempItems.clear();
        }
    }

    public void composeItems(){
        if(selectedItems.size() <= 1){
            return;
        }
        CompositeItem ci = new CompositeItem(selectedItems);
        items.removeAll(selectedItems);
        items.add(ci);
        selectedItems.clear();
        selectedItems.add(ci);
        ci.setSelected(true);
        recorder.addRecord(new ComposeRecord(ci, items));
        updateCanvas();
    }

    public void undo(){
        selectedItems.forEach(i -> i.setSelected(false));
        selectedItems.clear();
        recorder.undo();
        updateCanvas();
    }

    private void updateCanvas(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        items.forEach(i -> i.draw(gc));
        tempItems.forEach(i -> i.draw(gc));
    }

    private void initHandler(){
        canvas.setOnMouseMoved(evt -> {
            if(handler.mouseMoved(evt))
                updateCanvas();
        });
        canvas.setOnMouseClicked(evt -> {
            if(handler.mouseClicked(evt))
                updateCanvas();    
        });
        canvas.setOnMousePressed(evt -> {
            if(handler.mousePressed(evt))
                updateCanvas();    
        });
        canvas.setOnMouseDragged(evt -> {
            if(handler.mouseDragged(evt))
                updateCanvas();    
        });
        canvas.setOnMouseReleased(evt -> {
            if(handler.mouseReleased(evt))
                updateCanvas();    
        });
        canvas.setOnKeyPressed(evt -> {
            if(evt.getCode().equals(KeyCode.Z) && evt.isControlDown()){
                undo();
            }
            if(evt.getCode().equals(KeyCode.X) && evt.isControlDown()){
                composeItems();
            }
        });
    }

    private Canvas canvas;
    private Recorder recorder;
    private List<AbstractItem> items;
    private List<AbstractItem> tempItems;
    private List<AbstractItem> selectedItems;
    String state;
    private MouseHandler handler;
    private Map<String, MouseHandler> handlerMap;
}

class ComposeRecord implements Record {
    ComposeRecord(CompositeItem c, List<AbstractItem> items){
        this.c = c;
        this.items = items;
    }

    @Override
    public void undo(){
        items.remove(c);
        for(AbstractItem i : c.getChildren()){
            items.add(i);
            i.setSelected(false);
        }
    }

    private CompositeItem c;
    private List<AbstractItem> items;
}