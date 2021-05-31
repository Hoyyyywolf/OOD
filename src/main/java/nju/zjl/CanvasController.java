package nju.zjl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class CanvasController {
    public CanvasController(Canvas canvas){
        this.canvas = canvas;
        items = new LinkedList<>();
        tempItems = new LinkedList<>();
        selectedItems = new LinkedList<>();
        state = "";
        handler = new MoveTool(items, selectedItems);
        handlerMap = new HashMap<>();

        initHandler();
        initTool();
    }

    public void initTool(){
        handlerMap.put("line", new LineTool(items, tempItems));
        handlerMap.put("move", new MoveTool(items, selectedItems));
        handlerMap.put("triangle", new BinaryItemTool(items, tempItems, Triangle::new));
        handlerMap.put("rectangle", new BinaryItemTool(items, tempItems, Rectangle::new));
        handlerMap.put("ellipse", new BinaryItemTool(items, tempItems, Ellipse::new));
        handlerMap.put("text", new TextTool(items));
    }

    public void changeState(String s){
        if(!state.equals(s)){
            state = s;
            handler = handlerMap.get(s);
            tempItems.clear();
        }
    }

    private void updateCanvas(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        items.stream().forEach(i -> i.draw(gc));
        tempItems.stream().forEach(i -> i.draw(gc));
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
        canvas.setOnMouseDragReleased(evt -> {
            if(handler.mouseDragReleased(evt))
                updateCanvas();    
        });
    }

    private Canvas canvas;
    private List<AbstractItem> items;
    private List<AbstractItem> tempItems;
    private List<AbstractItem> selectedItems;
    String state;
    private MouseHandler handler;
    private Map<String, MouseHandler> handlerMap;
}