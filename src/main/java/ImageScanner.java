import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class ImageScanner {

    public static Color[][] scanImage(Image image, int cols, int rows) {

        Color[][] scannedGrid = new Color[cols][rows];
        PixelReader reader = image.getPixelReader();

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {

                if (x >= image.getWidth() || y >= image.getHeight()) {
                    continue;
                }

                Color pixelColor = reader.getColor(x, y);

                double r = pixelColor.getRed();
                double g = pixelColor.getGreen();
                double b = pixelColor.getBlue();

                if (r > 0.5 && g < 0.5 && b < 0.5) {
                    scannedGrid[x][y] = Color.RED;
                }
                else if (r < 0.4 && g < 0.4 && b < 0.4) {
                    scannedGrid[x][y] = Color.BLACK;
                }
                else {
                    scannedGrid[x][y] = null;
                }
            }
        }
        return scannedGrid;
    }
}