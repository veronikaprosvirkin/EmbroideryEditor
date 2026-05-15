import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.canvas.Canvas;

public class Main extends Application {

    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Редактор орнаменту української вишивки | Автор: Просвіркін Вероніка");
        EditorScreen editor = new EditorScreen();
        primaryStage.setScene(editor.createScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}