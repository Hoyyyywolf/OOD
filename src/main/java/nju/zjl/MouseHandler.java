package nju.zjl;

import javafx.scene.input.MouseEvent;

public interface MouseHandler {
    default boolean mouseMoved(MouseEvent evt){
        return false;
    }
    default boolean mouseClicked(MouseEvent evt){
        return false;
    }
    default boolean mousePressed(MouseEvent evt){
        return false;
    }
    default boolean mouseDragged(MouseEvent evt){
        return false;
    }
    default boolean mouseDragReleased(MouseEvent evt){
        return false;
    }
}
