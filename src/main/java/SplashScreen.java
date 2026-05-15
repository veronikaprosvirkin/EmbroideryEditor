import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class SplashScreen {
    private Stage window;
    private volatile boolean isSkipped = false;

    public SplashScreen(Stage window) {
        this.window = window;
    }
    private void skipAnimation() {
        if (!isSkipped) {
            isSkipped = true;
            openMainEditor();
        }
    }

    public Scene createSplashScene() {
        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: #FFFFFF;");
        Canvas canvas = new Canvas(600, 600);
        layout.setCenter(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image pattern = new Image("file:src/main/resources/splash_pattern.png");
        Color [][] scannedPattern = ImageScanner.scanImage(pattern, 40, 40);
        startDrawingAnimation(gc, scannedPattern);
        Scene splashScene = new Scene(layout, 1200, 700);
        splashScene.setOnMouseClicked(event -> skipAnimation());
        splashScene.setOnKeyPressed(event -> skipAnimation());

        splashScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                openMainEditor();
            }
        });

        Label hint = new Label("Press Enter or Click to Start");
        hint.setStyle("-fx-text-fill: gray; -fx-font-size: 14px; -fx-font-family: 'Segoe UI';");
        layout.setBottom(hint);
        BorderPane.setAlignment(hint, javafx.geometry.Pos.CENTER);
        return splashScene;
    }

    // Animation for drawing the scanned pattern on the canvas
    private void startDrawingAnimation(GraphicsContext gc, Color[][] scannedPattern) {
        new Thread(() -> {
            for (int y = 0; y < scannedPattern[0].length; y++) {
                for (int x = 0; x < scannedPattern.length; x++) {

                    if (isSkipped) {
                        return;
                    }
                    Color color = scannedPattern[x][y];

                    if (color != null) {
                        int finalX = x;
                        int finalY = y;

                        javafx.application.Platform.runLater(() -> {
                            gc.setFill(color);
                            gc.fillRect(finalX * 15, finalY * 15, 15, 15);
                        });
                        try {
                            Thread.sleep(15);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            javafx.application.Platform.runLater(() -> startBlinkingEffect(gc.getCanvas()));
        }).start();
    }

    private void startBlinkingEffect(Canvas canvas) {
        FadeTransition fade = new FadeTransition(Duration.seconds(1.5), canvas);
        fade.setFromValue(1.0);
        fade.setToValue(0.3);
        fade.setCycleCount(FadeTransition.INDEFINITE);
        fade.setAutoReverse(true);
        fade.play();
    }

    private void openMainEditor() {
        EditorScreen editor = new EditorScreen();
        window.setScene(editor.createScene());
        window.centerOnScreen();
    }

}