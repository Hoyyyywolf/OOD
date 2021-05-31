package nju.zjl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
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
        Button b = new Button("move");
        Button button = new Button("line");
        vBox.getChildren().addAll(b, button);
        scrollPane.setContent(canvas);
        root.setCenter(scrollPane);
        root.setLeft(vBox);
        CanvasController cc = new CanvasController(canvas);
        b.setOnMouseClicked(evt -> cc.changeState("move"));
        button.setOnMouseClicked(evt -> cc.changeState("line"));
        root.setPrefSize(800, 600);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        Platform.setImplicitExit(false);

        primaryStage.setOnCloseRequest(evt -> Platform.exit());
    }

}
