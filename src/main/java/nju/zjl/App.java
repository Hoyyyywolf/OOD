package nju.zjl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();
        ScrollPane scrollPane = new ScrollPane();
        Canvas canvas = new Canvas(2000, 2000);
        VBox vBox = new VBox();
        MenuBar mBar = new MenuBar();
        cc = new CanvasController(canvas);

        scrollPane.setContent(canvas);
        root.setCenter(scrollPane);
        root.setLeft(vBox);
        root.setTop(mBar);
        
        initButtons(vBox);
        initMenuBar(mBar);

        root.setPrefSize(800, 600);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        Platform.setImplicitExit(false);
        primaryStage.setOnCloseRequest(evt -> Platform.exit());
    }

    private void initButtons(VBox vBox){
        Button b1 = new Button("move");
        Button b2 = new Button("line");
        Button b3 = new Button("triangle");
        Button b4 = new Button("rectangle");
        Button b5 = new Button("ellipse");
        Button b6 = new Button("text");

        vBox.getChildren().addAll(b1, b2, b3, b4, b5, b6);
        vBox.setSpacing(20);
        
        b1.setOnMouseClicked(evt -> cc.changeState("move"));
        b2.setOnMouseClicked(evt -> cc.changeState("line"));
        b3.setOnMouseClicked(evt -> cc.changeState("triangle"));
        b4.setOnMouseClicked(evt -> cc.changeState("rectangle"));
        b5.setOnMouseClicked(evt -> cc.changeState("ellipse"));
        b6.setOnMouseClicked(evt -> cc.changeState("text"));
    }

    private void initMenuBar(MenuBar mBar){
        Menu fileMenu = new Menu("file");
        fileMenu.getItems().add(new MenuItem("save"));
        mBar.getMenus().add(fileMenu);
    }

    private CanvasController cc;
}
