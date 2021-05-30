package nju.zjl;

import java.util.HashMap;
import java.util.LinkedList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;

public class CanvasController {
    public CanvasController(Canvas canvas){
        this.canvas = canvas;
        items = new LinkedList<>();
        tempItems = new LinkedList<>();
        selectedItem = null;
        state = "move";
        handler = null;
        handlerMap = new HashMap<>();

        initHandler();
    }

    public void addHandler(String state, MouseHandler handler){
        handlerMap.put(state, handler);
    }

    public void changeState(String s){
        if(!state.equals(s)){
            tempItems.clear();
            handler = handlerMap.get(s);
            handler.init(tempItems);
        }
    }

    private void updateCanvas(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        items.stream().forEach(i -> i.draw(gc));
        tempItems.stream().forEach(p -> p.draw(gc));
        selectedItem.draw(gc);
    }

    private void initHandler(){
        canvas.setOnMouseMoved(evt -> {
            if(handler.mouseMoved(evt.getX(), evt.getY(), items, tempItems, selectedItem))
                updateCanvas();
        });
        canvas.setOnMouseClicked(evt -> {
            if(evt.getButton().equals(MouseButton.PRIMARY) && handler.mouseClicked(evt.getX(), evt.getY(), items, tempItems, selectedItem))
                updateCanvas();    
        });
    }

    private Canvas canvas;
    private LinkedList<AbstractItem> items;
    private LinkedList<AbstractItem> tempItems;
    private AbstractItem selectedItem;
    String state;
    private MouseHandler handler;
    private HashMap<String, MouseHandler> handlerMap;
}