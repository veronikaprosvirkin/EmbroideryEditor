import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javafx.scene.canvas.Canvas;
import java.awt.*;

public class Main extends Application {
    private int canvasSize = 800;
    private int cellSize = 20;
    private int columns = canvasSize/cellSize;
    private int rows = canvasSize/cellSize;
    private Color[][] grid = new Color[columns][rows];
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Редактор орнаменту української вишивки | Автор: Просвіркін Вероніка");

        BorderPane mainLayout = new BorderPane();
        Scene scene = new Scene(mainLayout, 1200, 700);
        Canvas canvas = new Canvas(canvasSize,canvasSize);
        mainLayout.setCenter(canvas);


        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}