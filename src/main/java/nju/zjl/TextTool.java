package nju.zjl;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;

public class TextTool implements MouseHandler {
    public TextTool(List<AbstractItem> items, Recorder recorder){
        this.items = items;
        this.recorder = recorder;
    }

    @Override
    public boolean mouseClicked(MouseEvent evt){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Text Input");
        dialog.setHeaderText("Enter Text:");
        Optional<String> res = dialog.showAndWait();
        if(res.isPresent()){
            Text text = new Text(evt.getX(), evt.getY(), res.get());
            items.add(text);
            recorder.addRecord(new CreateRecord(text, items));
            return true;
        }
        return false;
    }

    protected List<AbstractItem> items;
    protected Recorder recorder;
}
