package nju.zjl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;

public class CanvasController {
    public CanvasController(Canvas canvas){
        this.canvas = canvas;
        items = new LinkedList<>();
        tempItems = new LinkedList<>();
        selectedItems = new LinkedList<>();
        state = "move";
        handler = new MoveTool();
        handlerMap = new HashMap<>();

        initHandler();
        addTool("point", new LineTool(items, tempItems));
    }

    public void addTool(String state, MouseHandler handler){
        handlerMap.put(state, handler);
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
            if(handler.mouseMoved(evt.getX(), evt.getY()))
                updateCanvas();
        });
        canvas.setOnMouseClicked(evt -> {
            if(evt.getButton().equals(MouseButton.PRIMARY) && handler.mouseClicked(evt.getX(), evt.getY()))
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