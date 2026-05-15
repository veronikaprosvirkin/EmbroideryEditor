import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class EditorScreen {
    public static int canvasSize = 800;
    private static int cellSize = 20;
    private static int columns = canvasSize/cellSize;
    private static int rows = canvasSize/cellSize;
    public static Color[][] grid = new Color[columns][rows];

    public void drawGrid(GraphicsContext gc) {
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                gc.setFill(grid[i][j] != null ? grid[i][j] : Color.WHITE);
                gc.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
                gc.setStroke(Color.GRAY);
                gc.strokeRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }
    }

    public Scene createScene() {
        BorderPane mainLayout = new BorderPane();
        Scene scene = new Scene(mainLayout, 1200, 700);
        Canvas canvas = new Canvas(canvasSize,canvasSize);
        mainLayout.setCenter(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        setupDrawingInteractivity(canvas, gc);
        return scene;
    }
    private void setupDrawingInteractivity(Canvas canvas, GraphicsContext gc) {
        canvas.setOnMouseClicked(event -> paintPixel(event.getX(), event.getY(), gc));
        canvas.setOnMouseDragged(event -> paintPixel(event.getX(), event.getY(), gc));
    }

    private void paintPixel(double mouseX, double mouseY, GraphicsContext gc) {
        int x = (int) (mouseX / cellSize);
        int y = (int) (mouseY / cellSize);
        if (x >= 0 && x < columns && y >= 0 && y < rows) {
            grid[x][y] = Color.RED; //temporary color for testing
            drawGrid(gc);
        }
    }
}
