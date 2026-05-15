import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class EditorScreen {

    // VARIABLES
    public static int canvasSize = 800;
    private static int cellSize = 20;
    private static int columns = canvasSize/cellSize;
    private static int rows = canvasSize/cellSize;
    public static Color[][] grid = new Color[columns][rows];
    private Color currentColor = Color.RED;
    private FlowPane historyBox;
    private Canvas canvas;
    private Button lastSelectedButton = null;

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
        canvas = new Canvas(canvasSize,canvasSize);
        mainLayout.setCenter(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        setupDrawingInteractivity(canvas, gc);
        drawGrid(gc);

        mainLayout.setTop(createTopPanel());
        mainLayout.setLeft(createLeftPanel());
        mainLayout.setRight(createRightPanel());
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
            grid[x][y] = currentColor;
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
        rightPanel.setPrefWidth(200);

        Label paletteLabel = new Label("Palette");
        ColorPicker colorPicker = new ColorPicker(currentColor);
        createColorPicker(colorPicker);

        Label baseLabel = new Label("Base colors:");
        HBox baseColors = new HBox(5);
        baseColors.getChildren().addAll(
                createColorButton(Color.RED, "#FF0000"),
                createColorButton(Color.BLACK, "#000000"),
                createColorButton(Color.WHITE, "#FFFFFF"),
                createColorButton(Color.BLUE, "#0000FF")
        );

        Label historyLabel = new Label("History:");
        historyBox = new FlowPane(5, 5);

        rightPanel.getChildren().addAll(paletteLabel, colorPicker, baseLabel, baseColors, historyLabel, historyBox);
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

    // COLOR PICKER CONFIGURATION
    private ColorPicker createColorPicker( ColorPicker colorPicker) {
        colorPicker.setOnAction(event -> {
            currentColor = colorPicker.getValue();
            addColorToHistory(currentColor);

            if (canvas != null) {
                Platform.runLater(()-> canvas.requestFocus());
            }
        });
        return colorPicker;
    }

    private void addColorToHistory(Color currentColor) {
        Button colorBtn = createColorButton(currentColor, toHexString(currentColor));
        colorBtn.fire();
        historyBox.getChildren().add(0, colorBtn);
        if (historyBox.getChildren().size() > 10) {
            historyBox.getChildren().remove(10);
        }
    }

    private String toHexString(Color currentColor) {
        int r = (int) (currentColor.getRed() * 255);
        int g = (int) (currentColor.getGreen() * 255);
        int b = (int) (currentColor.getBlue() * 255);
        return String.format("#%02X%02X%02X", r, g, b);
    }


    private Button createColorButton(Color color, String colorHex) {
        Button btn = new Button();
        btn.setPrefSize(30, 30);

        String baseStyle = "-fx-background-color: " + colorHex + "; -fx-border-color: black; -fx-border-width: 1px;";
        String activeStyle = "-fx-background-color: " + colorHex + "; -fx-border-color: #009903; -fx-border-width: 2.5px;";

        btn.setStyle(baseStyle);

        btn.setUserData(baseStyle);

        btn.setOnAction(event -> {
            currentColor = color;

            if (lastSelectedButton != null) {
                lastSelectedButton.setStyle((String) lastSelectedButton.getUserData());
            }

            btn.setStyle(activeStyle);
            lastSelectedButton = btn;

            if (canvas != null) {
                Platform.runLater(()-> canvas.requestFocus());
            }
        });

        return btn;
    }

}
