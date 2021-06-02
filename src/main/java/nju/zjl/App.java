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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        scrollPane.setOnKeyPressed(evt -> canvas.getOnKeyPressed().handle(evt));
        root.setCenter(scrollPane);
        root.setLeft(vBox);
        root.setTop(mBar);

        initDrawButtons(vBox);
        initMenuBar(mBar);

        root.setPrefSize(800, 600);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Drawer");
        primaryStage.show();
        Platform.setImplicitExit(false);
        primaryStage.setOnCloseRequest(evt -> Platform.exit());
    }

    private void initDrawButtons(VBox vBox){
        String[] drawStrings = {"move", "line", "triangle", "rectangle", "ellipse", "text"};
        for(String s : drawStrings){
            Button b = imageButton("image/" + s + ".png", Constants.BUTTON_WIDTH_1);
            b.setOnMouseClicked(evt -> cc.changeState(s));
            vBox.getChildren().add(b);
        }
    }

    private Button imageButton(String imgName, double width){
        Button ret = new Button();
        Image img = new Image(App.class.getClassLoader().getResourceAsStream(imgName));
        ImageView iView = new ImageView(img);
        iView.setFitWidth(width);
        iView.setFitHeight(width);
        ret.setGraphic(iView);
        // ret.setOnMouseEntered(evt -> ret.setEffect(new DropShadow()));
        // ret.setOnMouseExited(evt -> ret.setEffect(null));
        return ret;
    }

    private void initMenuBar(MenuBar mBar){
        Menu fileMenu = new Menu("File");
        Menu optionMenu = new Menu("Option");
        MenuItem undo = new MenuItem("Undo         Ctrl+Z");
        undo.setOnAction(evt -> cc.undo());
        MenuItem compose = new MenuItem("Compose   Ctrl+X");
        compose.setOnAction(evt -> cc.composeItems());
        optionMenu.getItems().addAll(undo, compose);
        mBar.getMenus().addAll(fileMenu, optionMenu);
    }

    private CanvasController cc;
}
