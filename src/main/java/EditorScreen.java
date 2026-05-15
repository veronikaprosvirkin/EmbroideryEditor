import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

    // PANELS CONFIGURATION
    private HBox createTopPanel() {
        HBox topPanel = new HBox();
        topPanel.setPadding(new Insets(10));
        topPanel.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CCCCCC; -fx-border-width: 0 0 1px 0;");
        Label titleLabel = new Label("Редактор орнаменту: Піксельна вишивка | Автор: Просвіркін Вероніка");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        topPanel.getChildren().add(titleLabel);

        return topPanel;
    }

    private VBox createRightPanel() {
        VBox rightPanel = new VBox(10);
        rightPanel.setPadding(new Insets(10));
        rightPanel.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #bcbcbc; -fx-border-width: 0 0 0 1;");
        Label palette = new Label("Palette");
        palette.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        rightPanel.setPrefWidth(200);
        rightPanel.getChildren().add(palette);
        return rightPanel;
    }

    private VBox createLeftPanel() {
        VBox leftPanel = new VBox(10);
        leftPanel.setPadding(new Insets(10));
        leftPanel.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #bcbcbc; -fx-border-width: 0 1px 0 0;");
        Label tools = new Label("Tools");
        tools.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        leftPanel.setPrefWidth(200);
        leftPanel.getChildren().add(tools);
        return leftPanel;
    }
}
