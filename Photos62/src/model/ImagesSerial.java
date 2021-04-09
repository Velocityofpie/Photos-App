package model;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class ImagesSerial {
    private static final long serialVersionUID = 1L;
    private int width, height;
    private int[][] pixels;

    public ImagesSerial() {}


    public void setImage(Image image) {
        height = ((int) image.getHeight());
        width = ((int) image.getWidth());
        pixels = new int[width][height];

        PixelReader r = image.getPixelReader();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++){
                pixels[i][j] = r.getArgb(i, j);
            }
        }
    }


    public Image getImage() {
        WritableImage image = new WritableImage(width, height);

        PixelWriter w = image.getPixelWriter();
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                w.setArgb(i, j, pixels[i][j]);

        return image;
    }

    public int[][] getPixels() {
        return pixels;
    }
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }



    public boolean equals(ImagesSerial si) {
        if (width != si.getWidth()||height != si.getHeight() ){
            return false;
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (pixels[i][j] != si.getPixels()[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    }

