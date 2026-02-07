package utils;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageUtils {
    public static Color averageColor(Image image) {
        double redSum = 0;
        double greenSum = 0;
        double blueSum = 0;
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        int totalPixels = width * height;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = image.getPixelReader().getColor(x, y);
                if(color.getOpacity() == 0) {
                    totalPixels--;
                    continue;
                }
                redSum += color.getRed();
                greenSum += color.getGreen();
                blueSum += color.getBlue();
            }
        }

        return Color.color(redSum / totalPixels, greenSum / totalPixels, blueSum / totalPixels);
    }

    public static Image applyColor(Image image, Color color) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage coloredImage = new WritableImage(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color originalColor = image.getPixelReader().getColor(x, y);
                if(originalColor.getOpacity() == 0) {
                    coloredImage.getPixelWriter().setColor(x, y, originalColor);
                    continue;
                }
                coloredImage.getPixelWriter().setColor(x, y, color);
            }
        }

        return coloredImage;
    }
}
