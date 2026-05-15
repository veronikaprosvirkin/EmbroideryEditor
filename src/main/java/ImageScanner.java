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


                if (r > 0.8 && g < 0.2 && b < 0.2) {
                    scannedGrid[x][y] = Color.RED;
                } else if (r < 0.1 && g < 0.1 && b < 0.1) {
                    scannedGrid[x][y] = Color.BLACK;
                } else {
                    scannedGrid[x][y] = Color.rgb(255,244,181);
                }
            }
        }
        return scannedGrid;
    }
}